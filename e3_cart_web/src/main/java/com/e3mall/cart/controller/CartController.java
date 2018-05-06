package com.e3mall.cart.controller;

import com.e3mall.cart.service.CartService;
import com.e3mall.common.pojo.TbItem;
import com.e3mall.common.pojo.TbUser;
import com.e3mall.common.util.CookieUtils;
import com.e3mall.common.util.E3Result;
import com.e3mall.common.util.JsonUtils;
import com.e3mall.manager.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author WJX
 * @since 2018/5/3 15:09
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;

    @ApiOperation(notes = "添加购物车", value = "/add/{itemId}")
    @RequestMapping("/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        //如果是登录状态，把购物车写入redis
        if (user != null) {
            //保存到服务端
            cartService.addCart(user.getId(), itemId, num);
            //返回逻辑视图
            return "cartSuccess";
        }
        //如果未登录使用cookie
        //从cookie中取购物车列表
        List<TbItem> cartList = this.getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag = false;
        for (TbItem tbItem : cartList) {
            //如果存在数量相加
            if (tbItem.getId() == itemId.longValue()) {
                flag = true;
                //找到商品，数量相加
                tbItem.setNum(tbItem.getNum() + num);
                //跳出循环
                break;
            }
        }
        //如果不存在
        if (!flag) {
            //根据商品id查询商品信息。得到一个TbItem对象
            TbItem tbItem = itemService.getItemById(itemId);
            //设置商品数量
            tbItem.setNum(num);
            //取一张图片
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                tbItem.setImage(image.split(",")[0]);
            }
            //把商品添加到商品列表
            cartList.add(tbItem);
        }
        //写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回添加成功页面
        return "cartSuccess";
    }

    /**
     * 从cookie中取购物车列表的处理
     */
    private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, "cart", true);
        //判断json是否为空
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }
        //把json转换成商品列表
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    @ApiOperation(notes = "展示购物车列表", value = "/cart")
    @RequestMapping("/cart")
    public String showCatList(HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        //如果是登录状态
        if (user != null) {
            //从cookie中取购物车列表
            //如果不为空，把cookie中的购物车商品和服务端的购物车商品合并。
            cartService.mergeCart(user.getId(), cartList);
            //把cookie中的购物车删除
            CookieUtils.deleteCookie(request, response, "cart");
            //从服务端取购物车列表
            cartList = cartService.getCartList(user.getId());

        }
        //把列表传递给页面
        request.setAttribute("cartList", cartList);
        //返回逻辑视图
        return "cart";
    }

    @ApiOperation(notes = "更新购物车商品数量", value = "/update/num/{itemId}/{num}")
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num
            , HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.updateCartNum(user.getId(), itemId, num);
            return E3Result.ok();
        }
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历商品列表找到对应的商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId().longValue() == itemId) {
                //更新数量
                tbItem.setNum(num);
                break;
            }
        }
        //把购物车列表写回cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回成功
        return E3Result.ok();
    }

    @ApiOperation(notes = "删除购物车商品", value = "/delete/{itemId}")
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request,
                                 HttpServletResponse response) {
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历列表，找到要删除的商品
        for (TbItem tbItem : cartList) {
            if (tbItem.getId().longValue() == itemId) {
                //删除商品
                cartList.remove(tbItem);
                //跳出循环
                break;
            }
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回逻辑视图
        return "redirect:/cart/cart.html";
    }

    @ApiOperation(notes = "删除选中购物车商品", value = "/deleteSelect")
    @RequestMapping("/delSelect")
    @ResponseBody
    public E3Result deleteSelect(String ids, HttpServletRequest request,
                               HttpServletResponse response) {
        String[] idArray = ids.split(",");

        List<String> list = Arrays.asList(idArray);

        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.deleteCartItems(user.getId(), list);
            return  E3Result.ok();
        }
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);

        Map<String, TbItem> map = new HashMap<>();

        for (TbItem tbItem : cartList) {
            map.put(tbItem.getId().toString(), tbItem);
        }

        List<TbItem> tbItemList = new ArrayList<>();

        for (String str : list) {
            tbItemList.add(map.get(str));
        }

        for (TbItem item : tbItemList) {
            cartList.remove(item);
        }

        //把购物车列表写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        //返回逻辑视图
        return E3Result.ok();
    }

    @ApiOperation(notes = "清空购物车商品", value = "/deleteAll")
    @RequestMapping(value = "/delAll", method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteAll(HttpServletRequest request, HttpServletResponse response) {
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        if (user != null) {
            cartService.clearCartItem(user.getId());
            return E3Result.ok();
        }
        CookieUtils.deleteCookie(request, response, "cart");
//        return "redirect:/cart/cart.html";
        return E3Result.ok();
    }
}

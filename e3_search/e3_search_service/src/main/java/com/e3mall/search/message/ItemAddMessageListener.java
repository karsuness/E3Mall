package com.e3mall.search.message;

import com.e3mall.common.pojo.SearchItem;
import com.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author WJX
 * @since 2018/5/3 10:45
 */

/**
 * 商品添加时的消息监听
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            // 从消息中取出id
            String text = textMessage.getText();
            Long itemId = new Long(text);
            System.out.println(itemId);
            // 等待事务提交
            Thread.sleep(1000);
            // 根据商品id查询商品信息
            SearchItem searchItem = itemMapper.getItemById(itemId);
            // 创建一个文档对象
            SolrInputDocument document = new SolrInputDocument();
            // 向文档对象中添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            // 把文档写入索引库
            solrServer.add(document);
            // 提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

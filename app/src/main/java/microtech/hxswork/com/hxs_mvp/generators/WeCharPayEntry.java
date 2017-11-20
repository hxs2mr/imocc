package microtech.hxswork.com.hxs_mvp.generators;

import com.example.annotations.EnterGenerator;
import com.example.annotations.PayEntryGenerator;

import microtech.hxswork.com.latte.wechat.templates.WXEntrytemplate;
import microtech.hxswork.com.latte.wechat.templates.WXPayEntryTemplate;

/**
 * Created by microtech on 2017/11/17.
 */
@PayEntryGenerator(
        packageName =  "microtech.hxswork.com.hxs_mvp",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeCharPayEntry {
}

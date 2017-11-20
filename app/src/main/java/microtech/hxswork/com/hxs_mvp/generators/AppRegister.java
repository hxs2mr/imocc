package microtech.hxswork.com.hxs_mvp.generators;

import com.example.annotations.AppRegisterGenerator;
import com.example.annotations.EnterGenerator;

import microtech.hxswork.com.latte.wechat.templates.AppRegisterTemplate;
import microtech.hxswork.com.latte.wechat.templates.WXEntrytemplate;

/**
 * Created by microtech on 2017/11/17.
 */
@AppRegisterGenerator(
        packageName =  "microtech.hxswork.com.hxs_mvp",
        registerTemplete = AppRegisterTemplate.class
)
public interface AppRegister {
}

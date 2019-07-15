

# 腾讯开放平台



* 第1步：将请求的URI路径进行URL编码（URI不含host，URI示例：/v3/user/get_info）。
请开发者关注：URL编码注意事项，否则容易导致后面签名不能通过验证。

* 第2步：将除“sig”外的所有参数按key进行字典升序排列。 
注：除非OpenAPI文档中特别标注了某参数不参与签名，否则除sig外的所有参数都要参与签名。

* 第3步：将第2步中排序后的参数(key=value)用&拼接起来，并进行URL编码。
请开发者关注：URL编码注意事项，否则容易导致后面签名不能通过验证。

* 第4步：将HTTP请求方式（GET或者POST）以及第1步和第3步中的字符串用&拼接起来。
注：Java_SDK_V3.0.6仅支持POST方式，如果用GET可能导致一直计算sig不正确。 

demo

https://wiki.open.qq.com/wiki/%E8%85%BE%E8%AE%AF%E5%BC%80%E6%94%BE%E5%B9%B3%E5%8F%B0%E7%AC%AC%E4%B8%89%E6%96%B9%E5%BA%94%E7%94%A8%E7%AD%BE%E5%90%8D%E5%8F%82%E6%95%B0sig%E7%9A%84%E8%AF%B4%E6%98%8E


使用HMAC-SHA1加密算法，然后将加密后的字符串经过Base64编码

Java HMAC-SHA1

```
1. 原始请求信息：
appkey：228bf094169a40a3bd188ba37ebe8723

HTTP请求方式：GET

请求的URI路径（不含HOST）：/v3/user/get_info

请求参数：openid=11111111111111111&openkey=2222222222222222&appid=123456&pf=qzone&format=json&userip=112.90.139.30


2. 下面开始构造源串： 
第1步：将请求的URI路径进行URL编码，得到： %2Fv3%2Fuser%2Fget_info 

第2步：将除“sig”外的所有参数按key进行字典升序排列，排列结果为：appid，format，openid，openkey，pf，userip 

第3步：将第2步中排序后的参数(key=value)用&拼接起来：
appid=123456&format=json&openid=11111111111111111&openkey=2222222222222222&pf=qzone&userip=112.90.139.30 
然后进行URL编码（ 编码时请关注URL编码注意事项，否则容易导致后面签名不能通过验证），编码结果为：
appid%3D123456%26format%3Djson%26openid%3D11111111111111111%26openkey%3D2222222222222222%26pf%3Dqzone%26
userip%3D112.90.139.30


第4步：将HTTP请求方式，第1步以及第3步中的到的字符串用&拼接起来，得到源串：

GET&%2Fv3%2Fuser%2Fget_info&appid%3D123456%26format%3Djson%26openid%3D11111111111111111%26
openkey%3D2222222222222222%26pf%3Dqzone%26userip%3D112.90.139.30
```

# 淘宝开发平台

https://open.taobao.com/docV2.htm?docId=101617&docType=1

签名算法
为了防止API调用过程中被黑客恶意篡改，调用任何一个API都需要携带签名，TOP服务端会根据请求参数，对签名进行验证，签名不合法的请求将会被拒绝。TOP目前支持的签名算法有两种：MD5(sign_method=md5)，HMAC_MD5(sign_method=hmac)，签名大体过程如下：

对所有API请求参数（包括公共参数和业务参数，但除去sign参数和byte[]类型的参数），根据参数名称的ASCII码表的顺序排序。如：foo:1, bar:2, foo_bar:3, foobar:4排序后的顺序是bar:2, foo:1, foo_bar:3, foobar:4。
将排序好的参数名和参数值拼装在一起，根据上面的示例得到的结果为：bar2foo1foo_bar3foobar4。
把拼装好的字符串采用utf-8编码，使用签名算法对编码后的字节流进行摘要。如果使用MD5算法，则需要在拼装的字符串前后加上app的secret后，再进行摘要，如：md5(secret+bar2foo1foo_bar3foobar4+secret)；如果使用HMAC_MD5算法，则需要用app的secret初始化摘要算法后，再进行摘要，如：hmac_md5(bar2foo1foo_bar3foobar4)。
将摘要得到的字节流结果使用十六进制表示，如：hex(“helloworld”.getBytes(“utf-8”)) = “68656C6C6F776F726C64”
说明：MD5和HMAC_MD5都是128位长度的摘要算法，用16进制表示，一个十六进制的字符能表示4个位，所以签名后的字符串长度固定为32个十六进制字符。





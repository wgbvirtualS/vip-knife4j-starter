# 无代码侵入Knife4j

#### 介绍
这是一款改进的Knife4j，只需要Install本地仓库后，导入jar包并在配置文件中做简单配置即可直接享用Knife4j的所有功能，省去了Knife4j及Swagger的相关配置

## 1.在pom.xml 添加maven依赖

```xml
<dependency>
    <groupId>com.github.wgbvirtuals</groupId>
    <artifactId>vip-knife4j-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 2.配置

在这里我给大家提供了很多自由化的配置了,当然你也可以不用配置,因为所有的选项都是可选的


```xml
swagger.title = Swagger API Demo
swagger.description = This is swagger api describle demo
swagger.version = 1.1.1
knife4j:
  api-info:
    title: Knife4j API DEMO
    description: This is Knife4j api describle demo
    version: 1.0.0
```

**配置参数**

- basePackage(可选) 扫描的包路径,默认没有,扫描下面的所有的@Controller和@RequestMapping的映射
- apiInfo.title(可选)  接口的标题,具体就不多说了吧,可以自己试着玩,默认是`Swagger Restful API`
- apiInfo.description(可选) 接口的描述,默认是`This is a swagger api desc`
- apiInfo.version(可选) 接口的版本,默认是`1.0.0`
- apiInfo.termsOfService(可选) 服务条款的地址,默认没有
- apiInfo.license(可选) 证书名,默认没有
- apiInfo.licenseUrl(可选) 证书的url,默认没有
- author.name(可选)  作者,默认没有
- author.url(可选) 作者的url,默认没有
- author.email(可选)  作者的email,默认没有

## 3.启动和使用

启动的话,和前面没什么区别,使用也是和前面的那种方法是一样的,打开浏览器访问 

`http://localhost:端口号/doc.html`

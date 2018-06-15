# education-and-examination
前端用bootstrap框架+ajax异步请求

[演示链接](http://www.xidabadminton.top:8080/OnlineTest_6/index.jsp)

### 测试账号：管理员账号：yx   密码

### 功能分析

- 管理员可以向网上考试系统增加和删除教师和学生和增加试题。教师和学生要想使用网上考试系统必须先登录。

- 教师登录后，可以修改自己的登录密码；为学生的考试出题，并且形成题库；对试题进行修改；对试题进行按要求的查询。

- 学生登录后可以选择自己考试的课程；按照考试课程的名称，试卷名称考试；查看自己的分数；查询自已考过课程的试卷。

### 总体架构

SSH设计模式

- S: Struts2
- S: Spring
- H: hibernate

### 技术选型

数据库 : MySQL

Ajax解决方案 : JavaScript + JSON

### 问题


> - 为什么要采用ajax与ajax的优势？
>    - 以我自己的理解，其实ajax就是方便数据从前端传递到后端。在java中，主要有两种。第一种就是通过html的form表单，通过submit直接传输到后端，但是这个提交有一个坏处，就是服务器端（servlet端）返回数据的话只能是重新跳转一个页面(requet.getRequest.Dispatcher(url)。forward(request,response)或重定向response.sendRedirect(url))来进行处理，这样会很不方便，如果涉及要用户登录验证什么的话岂不是要重新跳转个页面来判断用户是否输入正确？这里的用户体验就很不好，而ajax则实现了异步请求，完美的解决了这个问题。

> SSH框架中Action、Service、Dao、struts、Spring之间的关系？
> - Action
>    - 看一下这个Action中的代码：这个类似于管理业务调度和管理跳转的，从这可以跳转到Service层，调用Service层中的方法，类似于三层结构中的B层，业务逻辑层。
> - Service
>    - 看一下service代码，每个service代码中要有个ser方法，Service层是管理具体的功能，Action只负责管理，而Service负责实施。
> - Dao
>    - dao层相当于三层中的D层，这里连接数据库的操作用的是hibernate，完成增删改查。Hibernate的最大好处就是根据数据库的表，反向生成实体类，并且还有关系在里面，还有就是它对数据的操作也很方便；
> - struts——控制界面和Action之间的联系
> - applicationContext.xml——控制struts和后台逻辑之间的关系
>    - 这里在Bean中进行了对Action的配置，和访问Service中的方法，在这里也就是spring，在service层和dao层都没有看到实例化某个类，所以spring的出现，减少了各个类中的调用关系，将这些关系都转化到了配置文件中，这样就简便多了。

> - action service dao 之间的关系？
>    - 首先 struts给action值（action就是struts），
>    - action是处理页面的，
>    - 通过execute()方法，
>    - action得到值然后经过调用service方法处理业务，
>    - service调用dao，
>    - dao处理数据库数据，
>    - 经过一番处理之后action将值return给页面；



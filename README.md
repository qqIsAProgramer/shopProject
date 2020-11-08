`shopProject`是基于`SpringBoot`和`MyBatis`的实现**用户模块**、**商品模块**、**下单模块**、**秒杀模块**的后端项目。

实现的功能有：

- 用户注册、登录
  - 通过短信验证码注册
- 创建、查询商品和查看商品列表
- 创建订单
  - 含有秒杀信息



该项目的结构是：

shop

- controller
  - VO
- dao
- error
- pojo
- response
- service
  - impl
  - Model
- validator (使用`hibernate-validator`通过注解来完成模型参数校验，暂时未完成)



**项目特点：**

- 该项目有一个很大的特点就是有**三类模型的转换**，分别是`Pojo`、`Model`、`VO`之间的转换：`Pojo`是写入数据库的模型，`Model`是中间进行操作的模型，`VO`是用来前端展示的模型。

- `insert`中设置`keyProperty="id" useGeneratedKeys="true"`使得插入完后的`Pojo`生成自增 id 。
- 数据库设计规范，设计时字段要设置为`not null`，并设置默认值，避免唯一索引在`null`情况下失效等类似场景。

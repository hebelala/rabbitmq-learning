# RabbitMQ Learning

## Server Operations

1. 启动rabbitmq和management容器

```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

2. 查看容器

```bash
docker container ls
```

3. 登录容器

```bash
docker exec -it d80a bash
```

4. 查看队列

```bash
rabbitmqctl list_queues
```

## Practices

* [Hello World](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)

* [Work Queues](https://www.rabbitmq.com/tutorials/tutorial-two-java.html)

* [Publish/Subscribe](https://www.rabbitmq.com/tutorials/tutorial-three-python.html)

* [Routing](https://www.rabbitmq.com/tutorials/tutorial-four-python.html)

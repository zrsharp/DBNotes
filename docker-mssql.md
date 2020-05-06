> 说明： 
>
> 内容来自  [sql server 官方文档](https://docs.microsoft.com/zh-cn/sql/linux/quickstart-install-connect-docker) ，由于微软的网站很卡，几乎进不去，所以我直接把参考文档搬过来了。
>
> sql server Linux 版官方只有 ubuntu 的，但是其他发行版可以通过 docker 进行安装（doker 安装请自行百度或者谷歌）。本人使用的是 Arch Linux，经过测试，完全可以正常运行。



# 拉取并运行容器映像

1. **从 Docker Hub 拉取 SQL Server 2019 Linux 容器映像**

```bash
sudo docker pull mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04
```



2. **使用 Docker 运行容器映像**

```bash
sudo docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=<YourStrong@Passw0rd>" \
   -p 1433:1433 --name sql1 \
   -d mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04
```



> 备注：密码应符合 SQL Server 默认密码策略，否则容器无法设置 SQL Server，将停止工作。 默认情况下，**密码的长度必须至少为 8 个字符，并且必须包含以下四种字符中的三种：大写字母、小写字母、十进制数字和符号**。 你可以通过执行 [docker logs](https://docs.docker.com/engine/reference/commandline/logs/) 命令检查错误日志。

> 备注：默认情况下，这会创建一个使用 SQL Server 2019 开发人员版的容器。



下表对前一个 docker run 示例中的参数进行了说明：

| 参数                                                     | 说明                                                         |
| :------------------------------------------------------- | :----------------------------------------------------------- |
| **-e “ACCEPT_EULA=Y"**                                   | 将 **ACCEPT_EULA** 变量设置为任意值，以确认接受[最终用户许可协议](https://go.microsoft.com/fwlink/?LinkId=746388)。 SQL Server 映像的必需设置。 |
| **-e "SA_PASSWORD="**                                    | 指定至少包含 8 个字符且符合 [SQL Server 密码要求](https://docs.microsoft.com/zh-cn/sql/relational-databases/security/password-policy?view=sql-server-ver15)的强密码。 SQL Server 映像的必需设置。 |
| **-p 1433:1433**                                         | 将主机环境中的 TCP 端口（第一个值）映射到容器中的 TCP 端口（第二个值）。 在此示例中，SQL Server 侦听容器中的 TCP 1433，并对主机上的端口 1433 公开。 |
| **--name sql1**                                          | 为容器指定一个自定义名称，而不是使用随机生成的名称。 如果运行多个容器，则无法重复使用相同的名称。 |
| **mcr.microsoft.com/mssql/server:2019-CU3-ubuntu-18.04** | SQL Server 2019 Ubuntu Linux 容器映像。                      |



3.  **要查看 Docker 容器，请使用 `docker ps` 命令。**

```bash
sudo docker ps -a
```



4. 如果“状态”列显示“正常运行”，则 SQL Server 将在容器中运行，并侦听“端口”列中指定的端口 。如果 SQL Server 容器的“状态”列显示“已退出”，则参阅[配置指南的疑难解答部分](https://docs.microsoft.com/zh-cn/sql/linux/sql-server-linux-configure-docker?view=sql-server-ver15#troubleshooting)。

-h（主机名）参数也非常有用，但为了简单起见，本教程中不使用它。 这会将容器的内部名称更改为一个自定义值。 也就是以下 Transact-SQL 查询中返回的名称：

```sql
SELECT @@SERVERNAME,
    SERVERPROPERTY('ComputerNamePhysicalNetBIOS'),
    SERVERPROPERTY('MachineName'),
    SERVERPROPERTY('ServerName')
```

将 `-h` 和 `--name` 设为相同的值是一种很好的方法，可以轻松地识别目标容器。

*个人建议：如果没有特别的需要，只是单纯做做课程任务，就别乱改这个，想折腾除外。*



# 更改 SA 密码

SA 帐户是安装过程中在 SQL Server 实例上创建的系统管理员。 创建 SQL Server 容器后，通过在容器中运行 `echo $SA_PASSWORD`，可发现指定的 `SA_PASSWORD` 环境变量。 出于安全考虑，请考虑更改 SA 密码。

1. 选择 SA 用户要使用的强密码。
2. 使用 `docker exec` 运行sqlcmd ，以使用 Transact-SQL 更改密码。 在下面的示例中，将旧密码 `<YourStrong!Passw0rd>` 和新密码 `<YourNewStrong!Passw0rd>` 替换为你自己的密码值。

```bash
sudo docker exec -it sql1 /opt/mssql-tools/bin/sqlcmd \
   -S localhost -U SA -P "<YourStrong@Passw0rd>" \
   -Q 'ALTER LOGIN SA WITH PASSWORD="<YourNewStrong@Passw0rd>"'
```



# 连接到 SQL Server

下列步骤在容器内部使用 SQL Server 命令行工具 **sqlcmd** 来连接 SQL Server。

1. 使用 `docker exec -it` 命令在运行的容器内部启动交互式 Bash Shell。 在下面的示例中，`sql1` 是在创建容器时由 `--name` 参数指定的名称。

```bash
sudo docker exec -it sql1 "bash"
```

2. 在容器内部使用 sqlcmd 进行本地连接。 默认情况下，sqlcmd 不在路径之中，因此需要指定完整路径。

```bash
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "<YourNewStrong@Passw0rd>"
```

>  提示：可以省略命令行上提示要输入的密码。

3. 如果成功，应会显示 sqlcmd 命令提示符：`1>`。



# 创建和查询数据

以下部分将引导你使用 sqlcmd 和 Transact-SQL 完成新建数据库、添加数据并运行简单查询的整个过程。



## 新建数据库

以下步骤创建一个名为 `TestDB` 的新数据库。

1. 在 sqlcmd 命令提示符中，粘贴以下 Transact-SQL 命令以创建测试数据库：

   ```sql
   CREATE DATABASE TestDB
   ```

2. 在下一行中，编写一个查询以返回服务器上所有数据库的名称：

   ```sql
   SELECT Name from sys.Databases
   ```

3. 前两个命令没有立即执行。 必须在新行中键入 `GO` 才能执行以前的命令：

   ```sql
   GO
   ```



## 插入数据

接下来创建一个新表 `Inventory`，然后插入两个新行。

1. 在 sqlcmd 命令提示符中，将上下文切换到新的 `TestDB` 数据库：

   ```sql
   USE TestDB
   ```

2. 创建名为 `Inventory` 的新表：

   ```sql
   CREATE TABLE Inventory (id INT, name NVARCHAR(50), quantity INT)
   ```

3. 将数据插入新表：

   ```sql
   INSERT INTO Inventory VALUES (1, 'banana', 150); INSERT INTO Inventory VALUES (2, 'orange', 154);
   ```

4. 要执行上述命令的类型 `GO`：

   ```sql
   GO
   ```



## 选择数据

现在，运行查询以从 `Inventory` 表返回数据。

1. 通过 sqlcmd 命令提示符输入查询，以返回 `Inventory` 表中数量大于 152 的行：

   ```sql
   SELECT * FROM Inventory WHERE quantity > 152;
   ```

2. 执行此命令：

   ```sql
   GO
   ```



## 退出 sqlcmd 命令提示符

1. 要结束 sqlcmd 会话，请键入 `QUIT`：

   ```sql
   QUIT
   ```

2. 要在容器中退出交互式命令提示，请键入 `exit`。 退出交互式 Bash Shell 后，容器将继续运行。



# 从容器外连接

还可以从支持 SQL 连接的任何 Linux、Windows 或 macOS 外部工具连接到 Docker 计算机上的 SQL Server 实例。

以下步骤在容器外使用 **sqlcmd** 连接在容器中运行的 SQL Server。 这些步骤假定你已在容器外安装了 SQL Server 命令行工具。 使用其他工具时，同样的原则依然适用，但连接过程因工具而异。

1. 查找承载容器的计算机的 IP 地址。 在 Linux 上，使用 **ifconfig** 或 **ip addr**。在 Windows 上，使用 **ipconfig**。

2. 对于本示例，请在客户端计算机上安装 **sqlcmd** 工具。 有关详细信息，请参阅[在 Windows 上安装 sqlcmd](https://docs.microsoft.com/zh-cn/sql/tools/sqlcmd-utility?view=sql-server-ver15) 或[在 Linux 上安装 sqlcmd](https://docs.microsoft.com/zh-cn/sql/linux/sql-server-linux-setup-tools?view=sql-server-ver15)。

3. 运行 sqlcmd，指定 IP 地址和映射容器中的端口 1433 的端口。 本例中为主机上的相同端口 1433。 如果在主机上指定了不同的映射端口，则在此处使用它。

   ```bash
   sqlcmd -S <ip_address>,1433 -U SA -P "<YourNewStrong@Passw0rd>"
   ```

4. 运行 Transact-SQL 命令。 完成后，键入 `QUIT`。

连接到 SQL Server 的其他常见工具包括：

- [Visual Studio Code](https://docs.microsoft.com/zh-cn/sql/linux/sql-server-linux-develop-use-vscode?view=sql-server-ver15)
- [适用于 Windows 的 SQL Server Management Studio (SSMS)](https://docs.microsoft.com/zh-cn/sql/linux/sql-server-linux-manage-ssms?view=sql-server-ver15)
- [Azure Data Studio](https://docs.microsoft.com/zh-cn/sql/azure-data-studio/what-is?view=sql-server-ver15)
- [mssql-cli（预览版）](https://github.com/dbcli/mssql-cli/blob/master/doc/usage_guide.md)
- [PowerShell Core](https://docs.microsoft.com/zh-cn/sql/linux/sql-server-linux-manage-powershell-core?view=sql-server-ver15)

> Linux 个人推荐 DBeaver，功能不比 Navicat 差，还支持生成 ER 图，如果只要不涉及到 sqlserver 专有的操作，这个软件都能完成。如果需要一些专门操作，只能使用 sqlcmd 或者 SSMS 了。



# 删除容器

```bash
sudo docker stop sql1
sudo docker rm sql1
```



# 后续步骤

有关如何将数据库备份文件还原到容器中的教程，请参阅[在 Linux Docker 容器中还原 SQL Server 数据库](https://docs.microsoft.com/zh-cn/sql/linux/tutorial-restore-backup-in-sql-server-container?view=sql-server-ver15)。 若要浏览其他方案（例如运行多个容器、数据暂留和故障排除），请参阅[在 Docker 上配置 SQL Server 容器映像](https://docs.microsoft.com/zh-cn/sql/linux/sql-server-linux-configure-docker?view=sql-server-ver15)。并且，请查看 [mssql-docker GitHub 存储库](https://github.com/Microsoft/mssql-docker)，了解资源、反馈和已知问题。
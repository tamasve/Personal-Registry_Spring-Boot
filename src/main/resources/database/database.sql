USE [master]
GO
/****** Object:  Database [personal-registry]    Script Date: 2024. 02. 05. 23:47:35 ******/
CREATE DATABASE [personal-registry]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'personal-registry', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\personal-registry.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'personal-registry_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\personal-registry_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [personal-registry] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [personal-registry].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [personal-registry] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [personal-registry] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [personal-registry] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [personal-registry] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [personal-registry] SET ARITHABORT OFF 
GO
ALTER DATABASE [personal-registry] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [personal-registry] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [personal-registry] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [personal-registry] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [personal-registry] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [personal-registry] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [personal-registry] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [personal-registry] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [personal-registry] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [personal-registry] SET  DISABLE_BROKER 
GO
ALTER DATABASE [personal-registry] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [personal-registry] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [personal-registry] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [personal-registry] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [personal-registry] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [personal-registry] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [personal-registry] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [personal-registry] SET RECOVERY FULL 
GO
ALTER DATABASE [personal-registry] SET  MULTI_USER 
GO
ALTER DATABASE [personal-registry] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [personal-registry] SET DB_CHAINING OFF 
GO
ALTER DATABASE [personal-registry] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [personal-registry] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [personal-registry] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [personal-registry] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'personal-registry', N'ON'
GO
ALTER DATABASE [personal-registry] SET QUERY_STORE = OFF
GO
USE [personal-registry]
GO
/****** Object:  Table [dbo].[address_types]    Script Date: 2024. 02. 05. 23:47:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[address_types](
	[address_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[address_type_value] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[address_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[addresses]    Script Date: 2024. 02. 05. 23:47:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[addresses](
	[address_id] [bigint] IDENTITY(1,1) NOT NULL,
	[city] [varchar](255) NULL,
	[number] [int] NOT NULL,
	[place] [varchar](255) NULL,
	[postal_code] [int] NOT NULL,
	[address_type_id] [bigint] NULL,
	[person_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[address_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[contact_types]    Script Date: 2024. 02. 05. 23:47:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[contact_types](
	[contact_type_id] [bigint] IDENTITY(1,1) NOT NULL,
	[contact_type_value] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[contact_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[contacts]    Script Date: 2024. 02. 05. 23:47:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[contacts](
	[contact_id] [bigint] IDENTITY(1,1) NOT NULL,
	[contact_value] [varchar](255) NOT NULL,
	[address_id] [bigint] NULL,
	[contact_type_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[contact_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[persons]    Script Date: 2024. 02. 05. 23:47:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[persons](
	[person_id] [bigint] IDENTITY(1,1) NOT NULL,
	[first_name] [varchar](255) NOT NULL,
	[id_card] [varchar](255) NOT NULL,
	[last_name] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[person_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[address_types] ON 

INSERT [dbo].[address_types] ([address_type_id], [address_type_value]) VALUES (1, N'Állandó')
INSERT [dbo].[address_types] ([address_type_id], [address_type_value]) VALUES (2, N'Ideiglenes')
SET IDENTITY_INSERT [dbo].[address_types] OFF
GO
SET IDENTITY_INSERT [dbo].[addresses] ON 

INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (3, N'Szeged', 4, N'Zigóta utca', 6724, 2, 1)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (4, N'Győr', 56, N'Széna tér', 3287, 1, 2)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (5, N'Jászberény', 3, N'Gilice utca', 8546, 1, 3)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (6, N'Medgyesegyháza', 75, N'Fő út', 5203, 2, 4)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (20, N'Szeged', 58, N'Fantom park', 5675, 2, 2)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (22, N'Karakószörcsökváraljaújhely', 8, N'Hentes tér', 6780, 1, 4)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (23, N'Budapest', 4, N'Bélapát tér', 1132, 1, 1)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (24, N'Jászberény', 77, N'Fakó utca', 2346, 1, 7)
INSERT [dbo].[addresses] ([address_id], [city], [number], [place], [postal_code], [address_type_id], [person_id]) VALUES (26, N'Bélapátfalva', 34, N'Dömötör sétány', 5401, 1, 8)
SET IDENTITY_INSERT [dbo].[addresses] OFF
GO
SET IDENTITY_INSERT [dbo].[contact_types] ON 

INSERT [dbo].[contact_types] ([contact_type_id], [contact_type_value]) VALUES (1, N'phone')
INSERT [dbo].[contact_types] ([contact_type_id], [contact_type_value]) VALUES (2, N'e-mail')
INSERT [dbo].[contact_types] ([contact_type_id], [contact_type_value]) VALUES (3, N'website')
INSERT [dbo].[contact_types] ([contact_type_id], [contact_type_value]) VALUES (4, N'viber')
INSERT [dbo].[contact_types] ([contact_type_id], [contact_type_value]) VALUES (6, N'twitter')
INSERT [dbo].[contact_types] ([contact_type_id], [contact_type_value]) VALUES (15, N'whatsapp')
SET IDENTITY_INSERT [dbo].[contact_types] OFF
GO
SET IDENTITY_INSERT [dbo].[contacts] ON 

INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (3, N'www.hozamban.hu', 3, 3)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (4, N'+36-70-589-5741', 4, 1)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (5, N'rekettyes@gmail.com', 4, 2)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (6, N'virgacs@gmail.com', 5, 2)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (7, N'www.sutapek.hu', 6, 3)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (61, N'www.juj.hu ', 3, 3)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (62, N'+36-70-968-9647 ', 3, 1)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (80, N'www.fantompark.hu ', 20, 3)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (87, N'+36-70-698-9959 ', 23, 1)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (88, N'makiluke@gmail.com ', 24, 2)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (89, N'www.ostvathpek.com', 22, 3)
INSERT [dbo].[contacts] ([contact_id], [contact_value], [address_id], [contact_type_id]) VALUES (91, N'+36-30-456-7895 ', 26, 1)
SET IDENTITY_INSERT [dbo].[contacts] OFF
GO
SET IDENTITY_INSERT [dbo].[persons] ON 

INSERT [dbo].[persons] ([person_id], [first_name], [id_card], [last_name]) VALUES (1, N'Jakab', N'445534KJ', N'Gipsz')
INSERT [dbo].[persons] ([person_id], [first_name], [id_card], [last_name]) VALUES (2, N'Zénó', N'976876GH', N'Beckó')
INSERT [dbo].[persons] ([person_id], [first_name], [id_card], [last_name]) VALUES (3, N'Balázs', N'453553KO', N'Németh')
INSERT [dbo].[persons] ([person_id], [first_name], [id_card], [last_name]) VALUES (4, N'Mihály', N'454535GT', N'Ostváth')
INSERT [dbo].[persons] ([person_id], [first_name], [id_card], [last_name]) VALUES (7, N'Luke', N'435435HU', N'Maki')
INSERT [dbo].[persons] ([person_id], [first_name], [id_card], [last_name]) VALUES (8, N'Béla', N'890121TG', N'Kovács')
SET IDENTITY_INSERT [dbo].[persons] OFF
GO
ALTER TABLE [dbo].[addresses]  WITH CHECK ADD  CONSTRAINT [FK3tnub56idln4409vf234m4uaa] FOREIGN KEY([address_type_id])
REFERENCES [dbo].[address_types] ([address_type_id])
GO
ALTER TABLE [dbo].[addresses] CHECK CONSTRAINT [FK3tnub56idln4409vf234m4uaa]
GO
ALTER TABLE [dbo].[addresses]  WITH CHECK ADD  CONSTRAINT [FK41l4n7dtgs89q3500yfxolvyd] FOREIGN KEY([person_id])
REFERENCES [dbo].[persons] ([person_id])
GO
ALTER TABLE [dbo].[addresses] CHECK CONSTRAINT [FK41l4n7dtgs89q3500yfxolvyd]
GO
ALTER TABLE [dbo].[contacts]  WITH CHECK ADD  CONSTRAINT [FKdgm0j5942ogm3acmpwj9loj86] FOREIGN KEY([address_id])
REFERENCES [dbo].[addresses] ([address_id])
GO
ALTER TABLE [dbo].[contacts] CHECK CONSTRAINT [FKdgm0j5942ogm3acmpwj9loj86]
GO
ALTER TABLE [dbo].[contacts]  WITH CHECK ADD  CONSTRAINT [FKg8bldr8bl797udndde99vjppa] FOREIGN KEY([contact_type_id])
REFERENCES [dbo].[contact_types] ([contact_type_id])
GO
ALTER TABLE [dbo].[contacts] CHECK CONSTRAINT [FKg8bldr8bl797udndde99vjppa]
GO
USE [master]
GO
ALTER DATABASE [personal-registry] SET  READ_WRITE 
GO

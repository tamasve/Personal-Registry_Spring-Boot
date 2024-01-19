USE [personal-registry]
GO
/****** Object:  Table [dbo].[address_types]    Script Date: 2024. 01. 19. 23:58:32 ******/
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
/****** Object:  Table [dbo].[addresses]    Script Date: 2024. 01. 19. 23:58:32 ******/
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
/****** Object:  Table [dbo].[contact_types]    Script Date: 2024. 01. 19. 23:58:32 ******/
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
/****** Object:  Table [dbo].[contacts]    Script Date: 2024. 01. 19. 23:58:32 ******/
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
/****** Object:  Table [dbo].[persons]    Script Date: 2024. 01. 19. 23:58:32 ******/
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

create database politica;

use politica;

create table politica(
					ID Int,
                    Candidato varchar(250),
                    Partido_Politico varchar(80),
                    Evento varchar (200),
                    Fecha_Evento date,
					Ubicacion varchar(200),
                    Asistentes_Estimados int,
                    Campana_activa boolean
					  );

select * from politica;
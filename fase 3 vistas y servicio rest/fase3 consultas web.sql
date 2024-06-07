select s.sucursal_nombre, md.medico_nombre, t.tipo_atencion_descripcion, m.mascota_nombre, d.dueno_nombre,
a.fecha_realizacion, a.box_atencion
from atencion a inner join sucursal s on a.sucursal_id = s.sucursal_id
inner join medico md on a.medico_id = md.medico_id
inner join tipo_atencion t on a.tipo_atencion_id = t.tipo_atencion_id
inner join mascota m on a.mascota_id = m.mascota_id
inner join dueno d on m.dueno_id = d.dueno_id
where s.sucursal_id = 1 and t.tipo_atencion_id = 1;
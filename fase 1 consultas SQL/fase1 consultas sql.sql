/*  1. Listar el nombre de una sucursal y sus médicos en base a su id. */

select medico_nombre, medico_apellidos, sucursal_nombre
from medico m inner join medico_sucursal ms
on m.medico_id = ms.medico_id
inner join sucursal s
on ms.sucursal_id = s.sucursal_id
where s.sucursal_id = 1;

/* 2. Listar los productos del inventario de una sucursal en base al id de la sucursal. */

select i.inventario_nombre, i.inventario_marca, t.tipo_producto_descripcion, sucursal_nombre 
from inventario i inner join sucursal s 
on i.sucursal_id = s.sucursal_id
inner join tipo_producto t
on i.tipo_producto_id = t.tipo_producto_id
where s.sucursal_id = 2; 

/* 3. Listar todas las mascotas indicando nombre , que tipo de atención que tuvieron (médica o 
cirugía) , y debe ser utilizado el id de la sucursal para filtrar la información(en el ejemplo se 
tomó el id de sucursal 1).*/

select m.mascota_nombre, md.medico_nombre, md.medico_apellidos, ta.tipo_atencion_descripcion
from mascota m inner join atencion a on m.mascota_id = a.mascota_id
inner join tipo_atencion ta on a.tipo_atencion_id = ta.tipo_atencion_id
inner join medico md on a.medico_id = md.medico_id
inner join sucursal s on s.sucursal_id = a.sucursal_id
where s.sucursal_id = 1;

 /* Listar la cantidad mascotas atendidas por tipo de mascota que tiene cada Sucursa */
 
 select count(m.tipo_mascota_id) as cantidad, t.tipo_mascota_descripcion, s.sucursal_nombre
 from atencion a inner join mascota m on a.mascota_id = m.mascota_id
 inner join sucursal s on s.sucursal_id = a.sucursal_id
 inner join tipo_mascota t on m.tipo_mascota_id = t.tipo_mascota_id
 group by s.sucursal_id, m.tipo_mascota_id;
 
 /* 5. Agrupar la cantidad de cirugías que se han realizado en una sucursal determinada, 
mostrando cantidad, el nombre del médico y la sucursal , estableciendo el orden de 
forma descendiente por la cantidad de cirugias de los 10 médicos */

select count(t.tipo_atencion_id) as atenciones, md.medico_nombre, s.sucursal_nombre
from atencion a inner join medico md on a.medico_id = md.medico_id
inner join medico_sucursal ms on md.medico_id = ms.medico_id
inner join sucursal s on ms.sucursal_id = s.sucursal_id
inner join tipo_atencion t on t.tipo_atencion_id = a.tipo_atencion_id
group by s.sucursal_id, md.medico_id
order by atenciones desc limit 10;




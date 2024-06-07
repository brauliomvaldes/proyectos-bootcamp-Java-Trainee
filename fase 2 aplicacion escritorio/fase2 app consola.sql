select i.inventario_nombre, i.inventario_marca, i.tipo_producto_id, i.inventario_existencia 
from inventario i inner join tipo_producto t on i.tipo_producto_id = t.tipo_producto_id
inner join sucursal s on i.sucursal_id = s.sucursal_id
where s.sucursal_id = 1;

SELECT mascota_nombre, tipo_mascota_descripcion, a.tipo_atencion_id, ta.tipo_atencion_descripcion
FROM mascota m inner join tipo_mascota t on t.tipo_mascota_id = m.tipo_mascota_id
inner join atencion a on a.mascota_id = m.mascota_id
inner join tipo_atencion ta on ta.tipo_atencion_id = a.tipo_atencion_id
where a.fecha_proxima_revision like "2024-06-10%";




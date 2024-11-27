                .data
slist:          .word   0 # puntero a la lista que maneja malloc y free
cclist:         .word   0 # puntero a la lista circular de categoria
wclist:         .word   0 # puntero a la lista circular de objetos

schedv:         .space  36

menu:           .ascii  "\nColecciones de objetos categorizados\n"
                .ascii  "====================================\n"
                .ascii  "1-Nueva categoria\n"
                .ascii  "2-Siguiente categoria\n"
                .ascii  "3-Anterior categoria\n"
                .ascii  "4-Listar categorias\n"
                .ascii  "5-Borrar categoria actual\n"
                .ascii  "6-Anexar objeto a la categoria actual\n"
                .ascii  "7-Listar objetos de la categoria\n"
                .ascii  "8-Borrar objeto de la categoria\n"
                .ascii  "9-Lista de errores\n"
                .ascii  "0-Salir\n\n"
                .asciiz "Ingrese la opcion deseada (0-9): "

errorMessage:   .asciiz "Error: "
return:         .asciiz "\n"
catNameMessage: .asciiz "\n Ingrese el nombre de una categoria: "
selCatMessage:  .asciiz "\n Se ha seleccionado la categoria: "
idObjMessage:   .asciiz "\n Ingrese el ID del objeto a eliminar: "
objNameMessage: .asciiz "\n Ingrese el nombre de un objeto: "
successMessage: .asciiz "La operación se realizo con exito\n\n"
noObjFound:     .asciiz "\n El objeto no fue encontrado en la lista\n"

errorList:      .ascii  "ERRORES:\n"
                .ascii  "201 - No hay categorias para seleccionar\n"
                .ascii  "301 - No hay categorias para listar\n"
                .ascii  "401 - No hay categorias para borrar\n"
                .ascii  "501 - No existen categorias para poder anexar objetos\n"
                .ascii  "601 - No hay categorias creadas\n"
                .ascii  "602 - No hay objetos para la categoria en curso\n"
                .asciiz "701 - No existen categorias para borrar\n"
flecha:         .asciiz "-->"
notFound:       .asciiz "\n No se encontro el objeto\n\n"

        .text
        .globl main
main:
        addi    $sp, $sp, -4			#Hacemos lugar para la pila de 4 bytes	
        li      $t0, 9                  	#Guardamos en $t0 el valor de 9 (cantidad de opciones del menu)
        sw      $t0, ($sp)			#Guardamos ese 9 ($t0) en el espacio de la pila ($sp)
        jal     initialize_schedv		#Saltamos a la sub rutina que inicializa todas las funciones de este programa

do2:
        la      $a0, menu			#Cargamos la etiqueta donde esta el menu de opciones	
        li      $v0, 4				#Mostramos por pantalla el menu
        syscall					#Damos orden al sistema para hacer la ejecucion
        li      $v0, 5				#Ingresamos un numero por pantalla
        syscall
        move    $a0, $v0			#Movemos ese numero ingresado al registro $a0
        beq     $a0, $0, endw2			#/* Si es 0 TERMINAMOS EL PROGRAMA
        blt     $a0, $0, do2			# Bloque de comparaciones para no salirnos de las opciones entre 1 y 9
        bgt     $a0, $t0, do2			#*/								       
        jal     manage_schedv			#Saltamos a la sub rutina que maneja las opciones del menu
        lw      $t0, ($sp)			#Cargamos en $t0 lo que esta en la pila+0 ( opciones del menú a estas instancias)
        j       do2				#Jump a do2

endw2:						#/*
        lw      $t0, ($sp)
        addi    $sp, $sp, 4    			# Este bloque Restaura la Pila y hace terminar el programa
        li      $v0, 10
        syscall					#*/



smalloc: #-------------------------------------------#
        lw      $t0, slist			#Cargamos la lista "SLIST" lista para memoria dinamica
        beqz    $t0, sbrk			#Si esta vacia, llamamos a sbrk para pedir memoria dinamica
        move    $v0, $t0			 
        lw      $t0, 12($t0)			
        sw      $t0, slist
        sw      $0, ($v0)
        sw      $0, 12($v0)
        jr      $ra

sbrk:
        li      $a0, 16                     	#Pedimos 16 Bytes
        li      $v0, 9				#llamamos a SBRK 
        syscall                             
        jr      $ra				#volvemos a getblock

sfree:
        lw      $t0, slist
        sw      $t0, 12($a0)
        sw      $a0, slist                  
        jr      $ra


addnode:
        addi    $sp, $sp, -8			#hacemos lugar en la pila para 2 Word
        sw      $ra, ($sp)			#Guardamos el retorno a "newCategory"
        sw      $a0, 4($sp)			#Guardamos la dirección 0x10010004 ( Con la categoria cargada )
        jal     smalloc				#Saltamos a Smalloc
        sw      $a1, 4($v0)                 	#guardamos lo que esta en $a1 y lo almacenamos en 4+$v0 de la memoria
        sw      $a2, 8($v0)                   	#guardamos lo que esta en $a2 y lo almacenamos en 8+$v0 de la memoria
        lw      $a0, 4($sp)			#cargamos al registro $t0 lo que este en la pila 4+($sp)
        lw      $t0, ($a0)                  	#cargamos lo que este dentro en la direccion de $a0 en $t0
        beqz    $t0, addnode_empty_list		#si $t0 es 0 saltamos e etiqueta "addnode_empty_list"

addnode_to_end:
        lw      $t1, ($t0)                 	#cargamos a registro la dirección del heap + 16 ( 0x100400010 )
                
        sw      $t1, 0($v0)			#Guardamos en memoria la dirección de $t0 ( 0x100400010 )
        sw      $t0, 12($v0)			#Guardamos la misma dirección pero en $v0 (una es la referencia al inicio)
                
        sw      $v0, 12($t1)			
        sw      $v0, 0($t0)			
        j       addnode_exit			#Salto a etiqueta

addnode_empty_list:
        sw      $v0, ($a0)			#guardamos en data ($a0) lo que esta en $v0
        sw      $v0, 0($v0)			#guardamos en heap ($v0) lo que esta en 0+$v0
        sw      $v0, 12($v0)			#guardamos en heap ($v0) lo que esta en 12+$v0
addnode_exit:
        lw      $ra, ($sp)			#cargamos de la pila+0 para retornar
        addi    $sp, $sp, 8			#re establecemos 8 bytes a la pila
        jr      $ra				#return a la dirección $ra que cargamos anteriormente


delnode:					#/*
        addi    $sp, $sp, -8
        sw      $ra, ($sp)
        sw      $a0, 4($sp)
        lw      $a0, 8($a0)                 
        jal     sfree                       
        lw      $a0, 4($sp)                 
        lw      $t0, 12($a0)                
        beq     $a0, $t0, delnode_point_self	# Codigo para borrar nodo
        lw      $t1, 0($a0)                 
        sw      $t1, 0($t0)                 
        sw      $t0, 12($t1)                
        lw      $t1, 0($a1)                 
        bne     $a0, $t1, delnode_exit      
        sw      $t0, ($a1)                  
        j       delnode_exit			#*/

delnode_point_self:
        sw      $0, ($a1)

delnode_exit:
        jal     sfree
        lw      $ra,($sp)
        addi    $sp, $sp, 8
        jr      $ra


getblock:
        addi    $sp, $sp, -4
        sw      $ra, ($sp)			#Guardamos la direccion de retorno en la pila (para volver a "newcategory")
        li      $v0, 4				#/* Cargamos mensaje "catNameMessage"
        syscall					#*/
        jal     smalloc				#Saltamos a Smalloc
        move    $a0, $v0			#Copiamos la direccion de $t0=0x10040000(dir del heap) en $a0  ##Volvemos aca luego de SBRK##
        li      $a1, 16				#Cargamos 16 de forma inmediata
        li      $v0, 8				#/*
        syscall					#pseudo instruccion para escribir por teclado "String" */
        move    $v0, $a0			#movemos la direccion del heap en $v0 0x10040000
        lw      $ra, ($sp)			#Cargamos el retorno a "newCategory"
        addi    $sp, $sp, 4			#Restrablecemos esos bytes de la pila
        jr      $ra				#Saltamos a "NewCategory"


initialize_schedv:		
        la      $t0, schedv			#Guarda la etiqueta donde esta "SCHEDV" = .Space 36
        la      $t1, newcategory		#/* 
        sw      $t1, ($t0)
        la      $t1, next_category
        sw      $t1, 4($t0)
        la      $t1, prev_category		# SCHEDV[0]= Newcategory
        sw      $t1, 8($t0)			# SCHEDV[1]= Next_category
        la      $t1, list_categories		# ....
        sw      $t1, 12($t0)
        la      $t1, del_category		# Almacena en SCHEDV en todo el .Space que declaramos
        sw      $t1, 16($t0)
        la      $t1, add_object
        sw      $t1, 20($t0)
        la      $t1, list_objects
        sw      $t1, 24($t0)
        la      $t1, del_object
        sw      $t1, 28($t0)
        la      $t1, list_errors
        sw      $t1, 32($t0)			#*/
        jr      $ra				


newcategory:
        addiu   $sp, $sp, -4			
        sw      $ra, ($sp)			#Guardamos la direccion de donde saltamos +4 en la pila (para volver a "manage_schedv")
        la      $a0, catNameMessage         	#Cargamos / apuntamos a etiqueta "nombre cat"
        jal     getblock			#saltamos a etiqueta
        move    $a2, $v0                        #movemos la dir del heap 0x10040000 a $a2
        la      $a0, cclist                 	#apuntamos a la dirección donde esta cclist
        li      $a1, 0                       	#Cargamos la primer categoria en 0x10010004
        jal     addnode				#Saltamos a función "Addnode"
        lw      $t0, wclist			#Cargamos en $t0 el primer lugar de "Wclist"
        bnez    $t0, newcategory_end		#Sino es igual a cero saltamos a etiqueta	
        sw      $v0, wclist                 	#guardamos en data Value+8 los objetos de wclist

newcategory_end:
        li      $v0, 0                  	   
        lw      $ra, ($sp)			#Cargamos de la pila+0 una dirección de retorno
        addiu   $sp, $sp, 4			#re establecemos a la pila 4 bytes
        jr      $ra				#Saltamos a la dirección anteriormente cargada

list_categories:
        lw      $t0, cclist			#Carga en $t0 la lista de las categorias

if1:
        bne     $t0, $0, endif1             	#Si la lista NO es igual a 0 salta a etiqueta
        la      $a0, errorMessage		#Carga etiqueta de error
        li      $v0, 4				#/*
        syscall					#Muestra por pantalla ese mensaje "error"#
        li      $a0, 301			#Cargamos 301 ( error 301 )
        li      $v0, 1				#/*
        syscall					#Mostramos por pantalla el numero 301*/
        jr      $ra				#Retorno (despues del JALR)

endif1:
        move    $t1, $t0                    	#Movemos lo que esta en $t0 a $t1

do1:
        la      $a0, flecha			#Apuntamos a la "flecha" para señalar elementos
        li      $v0, 4				#/*
        syscall					#Mostramos por pantalla flecha --> */
        lw      $a0, 8($t0)			#Cargamos del Heap la categoría cargada anteriormente
        li      $v0, 4				#/*
        syscall					#Mostramos por pantalla las categorías*/
        la      $a0, return			#Aputamos a la etiqueta donde hay un pequeño salto de linea
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla*/
        lw      $t0, 12($t0)                	

while1:
        beq     $t0, $t1, endw1             	#Si $t0="categorias" es igual a $t1="final del la lista" saltamos( porque no hay mas categorias)
        j       do1				#Sino, Jump "do1" para seguir mostrando las otras categorias

endw1:
        jr      $ra				#Retorno


manage_schedv:  				#PANEL DE MANEJO DE LAS SUB RUTINAS
        
        addi    $sp, $sp, -4 			#Hacemos lugar en la pila
        sw      $ra, ($sp)			#guardamos lo que este en $ra en la posicion 0 de $sp (referencia a funcion "do2")
        addi    $t0, $a0, -1			#Restamos 1 $a0 y lo guardamos en $t0 para trabajar entre 0 y 8 ( las funciones )
        la      $t1, schedv                 	#Apuntamos de nuevo a SCHEVD pero en $t1
        sll     $t0, $t0, 2                 	#Corrimiento logico ( Multiplicar el índice por 4 calcula el desplazamiento necesario para acceder al índice correcto en schedv //sino hacer mul $t0,$t0,4)
        add     $t0, $t0, $t1                   #Sumamos la direccion de la etiqueta del SCHEDV a $t0 que es el resultado de restar 1 a la opcion del menu
        lw      $t0, ($t0)                  	#Cargamos en $t0 lo que esta en $t0 osea cagamos la opcion que elegimos del menu
        jalr    $t0				#Saltamos a la direccion de $t0 en caso que sea 1 iria al "array" [0] que seria NewCategory y etc.. y guardamos PC+4 = $ra
        lw      $ra, ($sp)			#Cargamos de la pila+0 otra dirección de retorno
        addi    $sp, $sp, 4			#Devolvemos otros 4 bytes de la pila
        jr      $ra				#Saltamos a esta nueva dirección

next_category:
        lw      $t0, wclist                	#Cargamos en $t0 lo que esta almacenado Wclist
        beq     $t0, $0, no_wclist		#Si $t0 es igual a $0 salta etiqueta
        lw      $t1, 12($t0)                	#Cargamos en $t1 la cateogira 12($t0)
        j       end_select_cat			#Salto a etiqueta

prev_category:
        lw      $t0, wclist                 	#Cargamos el contenido de Wclist en $t0
        beq     $t0, $0, no_wclist		#Si $t0 es igual a $0 salta a etiqueta
        lw      $t1, ($t0)                  	#Cargamos en $t1 el contenido de $t0+0
        j       end_select_cat			#Salto a etiqueta

end_select_cat:
        la      $a0, selCatMessage		#Apuntamos a etiqueta
        li      $v0, 4				#/*
        syscall					#Mostramos por pantalla*/
        sw      $t1, wclist			#Guardamos en $t1 lo que este en wclist
        lw      $a0, 8($t1)			#Cargamos en $a0 el contenido de 8($t1) ósea la siguiente
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla*/
        la      $a0, return			#Cargamos el salto de linea
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla*/
        jr      $ra				#Retorno

no_wclist:
        la      $a0, errorMessage		#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Mostramos por pantalla mensaje de error*/
        li      $a0, 201			#Cargamos el 201 ( error de tipo no hay categoria )
        li      $v0, 1				#/*
        syscall					#Mostramos por pantalla el numero del error*/
        jr      $ra				#Retornamos a Manager_schedv


add_object:
        lw      $s0, wclist           		#Cargmos en $s0 la lista de objetos  (opción 6)    
        bne     $s0, $0,add_object_if_cat   	#Si $s0 NO es igual a cero saltamos a etiqueta
        la      $a0, errorMessage		#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Mostramos por pantalla el mensaje "error"*/
        li      $a0, 501			#Cargamos de forma inmediata el numero 501
        li      $v0, 1				#/*
        syscall					#Mostramos el numero por pantalla*/
        jr      $ra				#Retorno

add_object_if_cat:
        addi    $sp, $sp, -8			#Pedimos 8 bytes a la pila
        sw      $ra, ($sp)			#Guardamos el retorno a .... en la pila
        sw      $s0, 4($sp)
        la      $a0, objNameMessage
        jal     getblock              		#Saltamos a Getblock      
        lw      $s0, 4($sp)

        la      $a0, 4($s0)                 
        move    $a2, $v0                    
        lw      $t0, ($a0)                  

if2:
        bne     $t0, $0, add_last_object    
        li      $a1, 1
        jal     addnode
        j       end_add_object

add_last_object:
        lw      $t0, ($t0)	                
        lw      $a1, 4($t0)	                
        addi    $a1, $a1, 1
        jal     addnode				#Salto

end_add_object:
        lw      $ra, ($sp)			#Cargamos a registro retorno a .... de la pila
        addi    $sp, $sp, 8			#Restablecemos 8 bytes de la pila
        jr      $ra				#retorno


list_objects:
        lw      $t0, wclist     		#Cargamos en $to la Wclist (opción 7)         
        beq     $t0, $0, list_obj_no_cat    	#Si $t0 es igual a cero
        lw      $t1, 4($t0)
        move    $t2, $t1
        beq     $t1, $0, list_obj_no_objs	#Si $t1 es igual a $0 saltamos

do3:
        lw      $a0, 4($t1)                 
        li      $v0, 1
        syscall
        la      $a0, flecha			#Apuntamos a flecha -->
        li      $v0, 4				#/*
        syscall					#La mostramos por pantalla*/
        lw      $a0, 8($t1)
        li      $v0, 4
        syscall
        la      $a0, return			#Apuntamos al salto de linea
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla
        lw      $t1, 12($t1)                

w3:
        beq     $t1, $t2, endw3			#Si son iguales salto a etiqueta
        j       do3				#Salto

endw3:
        jr      $ra				#retorno

list_obj_no_cat:
        la      $a0, errorMessage		#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Mostramos por pantalla "error"*/
        li      $a0, 601			#Cargamos de forma inmediata 601
        li      $v0, 1				#/*
        syscall					#Mostramos por pantalla el numero*/
        jr      $ra				#Retorno

list_obj_no_objs:
        la      $a0, errorMessage		#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Mostramos el mensaje por pantalla*/
        li      $a0, 602			#Cargamos el numero 602
        li      $v0, 1				#/*
        syscall					#Mostramos por pantalla numero*/
        jr      $ra				#Retorno

del_object:
        lw      $t0, wclist			#Cargamos en $t0 la lista de objetos
        bne     $t0, $0, check_if_objlist   	#Si $t0 NO es igual a 0 saltamos a etiqueta
        la      $a0, errorMessage		#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla*/
        li      $a0, 701			#Cargamos inmediatamente el numero 701
        li	$v0, 1				#/*
        syscall					#Lo mostramos por pantalla*/
        jr      $ra				#Retorno

check_if_objlist:
        addi    $t5, $t0, 4                 
        lw      $t0, 4($t0)                 
        bne     $t0, $0, ask_id     		#Si $t0 no es igual a $0 saltamos        
        la      $a0, errorMessage		#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla*/
        li      $a0, 702			#Cargamos inmediatamente el numero 701
        li      $v0, 1				#/*
        syscall					#Lo mostramos por pantalla*/
        jr      $ra				#Retorno

ask_id:
        addi    $sp, $sp, -4			#Pedimos memoria a la pila de 4 bytes
        sw      $ra, ($sp)			#Guardamos retorno
        la      $a0, idObjMessage		#Apuntamos a mensaje de ID
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla*/
        li      $v0, 5				#/*
        syscall					#Pedimos un ID por teclado*/
        move    $t7, $v0			#Movemos ese numero ingresado a $t7

search_delete_obj:
        move    $t1, $t0                    

do4:
        lw      $t6, 4($t0)                 
        beq     $t6, $t7, obj_founded       
        lw      $t0, 12($t0)                
        beq     $t1, $t0, obj_not_founded
        j       do4

obj_not_founded:
        la      $a0, notFound			#Cargamos etiqueta de notFound
        li      $v0, 4				#/*
        syscall					#Imprimimos por pantalla */
        j       end_del_obj			#Salto 

obj_founded:
        move    $a0, $t0                    
        move    $a1, $t5                    
        jal     delnode				#Salto

end_del_obj:
        lw      $ra, ($sp)			#Cargamos retorno a... (ver donde)
        addi    $sp, $sp, 4			#Restablecemos la pila en 4bytes
        jr      $ra				#Retorno

del_category:
        
        la      $t0, wclist           		#Cargamos etiqueta de Wclist      
        lw      $t1, ($t0)                  	#Cargamos en $t1 lo que este almacenado en 0+$t0 del heap
        bne     $t1, $0, del_cat_objects	#Si $t1 NO es igual a $0 saltamos del_cat_objects
        la      $a0, errorMessage		#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Mostramos el mensaje por pantalla*/
        li      $a0, 401			#Cargamos inmediatamente el numero 401
        li      $v0, 1				#/*
        syscall					#Mostramos el numero por pantalla*/
        jr      $ra				#Retorno

del_cat_objects:
        addi    $sp, $sp, -12			#Reestablecemos la pila 12 bytes
        sw      $ra, ($sp)			#Guardamos en memoria el retorno ( Del_cat_objets)
        sw      $t0, 4($sp)                 	#Guardamos en memoria lo que esta en $t0 dentro de 4+pila
        sw      $t1, 8($sp)                 	#Guardamos en memoria lo que esta en $t1 dentro de 8+pila
        addi    $t2, $t1, 4                 	#Siguiente elemento

while5:
        lw      $t7, ($t2)                  	
        beq     $t7, $0, del_current_cat    	#Si $t7 es 0 saltamos
        move    $a0, $t7                    
        move    $a1, $t2                    
        jal     delnode                     	#Salto
        j       while5				#Salto

del_current_cat:
        
        lw      $t0, cclist              	#Cargamos la Cclist   
        lw      $t0, 12($t0)                
        sw      $t0, cclist                 

        lw      $a0, 8($sp)                 
        lw      $a1, 4($sp)                 
        jal     delnode                    	#Salto 
        
        lw      $t0, 4($sp)
        lw      $t1, ($t0)                  
        bne     $t1, $0, end_del_cat        	#Si $t1 No es igual a $0 saltamos
        sw      $0, cclist
        j       end_del_cat			#Salto

end_del_cat:
        lw      $ra, ($sp)			#cargamos retorno
        addi    $sp, $sp, 12			#Restablecemos la pila en 12 bytes
        jr      $ra				#Retorno

list_errors:
        la      $a0, errorList			#Apuntamos a mensaje de error
        li      $v0, 4				#/*
        syscall					#Lo mostramos por pantalla*/
        jr      $ra				#Retorno

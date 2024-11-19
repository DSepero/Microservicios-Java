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

#newcategory -
#listcategory -
#delcategory -
#nextcategory -
#prevcategory -
#newobject -
#listobjects -
#delobjet -
#falta mejorar como se presenta el output
        .text
        .globl main
main:
        addi    $sp, $sp, -4		
        li      $t0, 9                  
        sw      $t0, ($sp)				
        jal     initialize_schedv	

do2:
        la      $a0, menu		
        li      $v0, 4			
        syscall
        li      $v0, 5			
        syscall
        move    $a0, $v0		
        beq     $a0, $0, endw2		
        blt     $a0, $0, do2		
        bgt     $a0, $t0, do2		
        jal     manage_schedv		
        lw      $t0, ($sp)		
        j       do2

endw2:
        lw      $t0, ($sp)
        addi    $sp, $sp, 4
        li      $v0, 10
        syscall

smalloc:
        lw      $t0, slist
        beqz    $t0, sbrk
        move    $v0, $t0
        lw      $t0, 12($t0)
        sw      $t0, slist
        sw      $0, ($v0)
        sw      $0, 12($v0)
        jr      $ra

sbrk:
        li      $a0, 16                     
        li      $v0, 9
        syscall                             
        jr      $ra

sfree:
        lw      $t0, slist
        sw      $t0, 12($a0)
        sw      $a0, slist                  
        jr      $ra


addnode:
        addi    $sp, $sp, -8
        sw      $ra, ($sp)
        sw      $a0, 4($sp)
        jal     smalloc
        sw      $a1, 4($v0)                 
        sw      $a2, 8($v0)                 
        lw      $a0, 4($sp)
        lw      $t0, ($a0)                  
        beqz    $t0, addnode_empty_list

addnode_to_end:
        lw      $t1, ($t0)                 
                
        sw      $t1, 0($v0)
        sw      $t0, 12($v0)
                
        sw      $v0, 12($t1)
        sw      $v0, 0($t0)
        j       addnode_exit

addnode_empty_list:
        sw      $v0, ($a0)
        sw      $v0, 0($v0)
        sw      $v0, 12($v0)
addnode_exit:
        lw      $ra, ($sp)
        addi    $sp, $sp, 8
        jr      $ra


delnode:
        addi    $sp, $sp, -8
        sw      $ra, ($sp)
        sw      $a0, 4($sp)
        lw      $a0, 8($a0)                 
        jal     sfree                       
        lw      $a0, 4($sp)                 
        lw      $t0, 12($a0)                
        beq     $a0, $t0, delnode_point_self
        lw      $t1, 0($a0)                 
        sw      $t1, 0($t0)                 
        sw      $t0, 12($t1)                
        lw      $t1, 0($a1)                 
        bne     $a0, $t1, delnode_exit      
        sw      $t0, ($a1)                  
        j       delnode_exit

delnode_point_self:
        sw      $0, ($a1)

delnode_exit:
        jal     sfree
        lw      $ra,($sp)
        addi    $sp, $sp, 8
        jr      $ra


getblock:
        addi    $sp, $sp, -4
        sw      $ra, ($sp)
        li      $v0, 4
        syscall
        jal     smalloc
        move    $a0, $v0
        li      $a1, 16
        li      $v0, 8
        syscall
        move    $v0, $a0
        lw      $ra, ($sp)
        addi    $sp, $sp, 4
        jr      $ra


initialize_schedv:		
        la      $t0, schedv
        la      $t1, newcategory
        sw      $t1, ($t0)
        la      $t1, next_category
        sw      $t1, 4($t0)
        la      $t1, prev_category
        sw      $t1, 8($t0)
        la      $t1, list_categories
        sw      $t1, 12($t0)
        la      $t1, del_category
        sw      $t1, 16($t0)
        la      $t1, add_object
        sw      $t1, 20($t0)
        la      $t1, list_objects
        sw      $t1, 24($t0)
        la      $t1, del_object
        sw      $t1, 28($t0)
        la      $t1, list_errors
        sw      $t1, 32($t0)
        jr      $ra

newcategory:
        addiu   $sp, $sp, -4
        sw      $ra, ($sp)
        la      $a0, catNameMessage         
        jal     getblock
        move    $a2, $v0                    
        la      $a0, cclist                 
        li      $a1, 0                      
        jal     addnode
        lw      $t0, wclist
        bnez    $t0, newcategory_end
        sw      $v0, wclist                 

newcategory_end:
        li      $v0, 0                      
        lw      $ra, ($sp)
        addiu   $sp, $sp, 4
        jr      $ra

list_categories:
        lw      $t0, cclist

if1:
        bne     $t0, $0, endif1             
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $a0, 301
        li      $v0, 1
        syscall
        jr      $ra

endif1:
        move    $t1, $t0                    

do1:
        la      $a0, flecha
        li      $v0, 4
        syscall
        lw      $a0, 8($t0)
        li      $v0, 4
        syscall
        la      $a0, return
        li      $v0, 4
        syscall
        lw      $t0, 12($t0)                

while1:
        beq     $t0, $t1, endw1             
        j       do1

endw1:
        jr      $ra


manage_schedv:
        
        addi    $sp, $sp, -4
        sw      $ra, ($sp)
        addi    $t0, $a0, -1
        la      $t1, schedv                 
        sll     $t0, $t0, 2                 
        add     $t0, $t0, $t1               
        lw      $t0, ($t0)                  
        jalr    $t0
        lw      $ra, ($sp)
        addi    $sp, $sp, 4
        jr      $ra

next_category:
        lw      $t0, wclist                 
        beq     $t0, $0, no_wclist
        lw      $t1, 12($t0)                
        j       end_select_cat

prev_category:
        lw      $t0, wclist                 
        beq     $t0, $0, no_wclist
        lw      $t1, ($t0)                  
        j       end_select_cat

end_select_cat:
        la      $a0, selCatMessage
        li      $v0, 4
        syscall
        sw      $t1, wclist
        lw      $a0, 8($t1)
        li      $v0, 4
        syscall
        la      $a0, return
        li      $v0, 4
        syscall
        jr      $ra

no_wclist:
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $a0, 201
        li      $v0, 1
        syscall
        jr      $ra


add_object:
        lw      $s0, wclist                 
        bne     $s0, $0,add_object_if_cat   
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $a0, 501
        li      $v0, 1
        syscall
        jr      $ra

add_object_if_cat:
        addi    $sp, $sp, -8
        sw      $ra, ($sp)
        sw      $s0, 4($sp)
        la      $a0, objNameMessage
        jal     getblock                    
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
        jal     addnode

end_add_object:
        lw      $ra, ($sp)
        addi    $sp, $sp, 8
        jr      $ra


list_objects:
        lw      $t0, wclist                 
        beq     $t0, $0, list_obj_no_cat    
        lw      $t1, 4($t0)
        move    $t2, $t1
        beq     $t1, $0, list_obj_no_objs

do3:
        lw      $a0, 4($t1)                 
        li      $v0, 1
        syscall
        la      $a0, flecha
        li      $v0, 4
        syscall
        lw      $a0, 8($t1)
        li      $v0, 4
        syscall
        la      $a0, return
        li      $v0, 4
        syscall
        lw      $t1, 12($t1)                

w3:
        beq     $t1, $t2, endw3
        j       do3

endw3:
        jr      $ra

list_obj_no_cat:
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $a0, 601
        li      $v0, 1
        syscall
        jr      $ra

list_obj_no_objs:
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $a0, 602
        li      $v0, 1
        syscall
        jr      $ra

del_object:
        lw      $t0, wclist
        bne     $t0, $0, check_if_objlist   
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $v0, 701
        jr      $ra

check_if_objlist:
        addi    $t5, $t0, 4                 
        lw      $t0, 4($t0)                 
        bne     $t0, $0, ask_id             
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $a0, 702
        li      $v0, 1
        syscall
        jr      $ra

ask_id:
        addi    $sp, $sp, -4
        sw      $ra, ($sp)
        la      $a0, idObjMessage
        li      $v0, 4
        syscall
        li      $v0, 5
        syscall
        move    $t7, $v0

search_delete_obj:
        move    $t1, $t0                    

do4:
        lw      $t6, 4($t0)                 
        beq     $t6, $t7, obj_founded       
        lw      $t0, 12($t0)                
        beq     $t1, $t0, obj_not_founded
        j       do4

obj_not_founded:
        la      $a0, notFound
        li      $v0, 4
        syscall
        j       end_del_obj

obj_founded:
        move    $a0, $t0                    
        move    $a1, $t5                    
        jal     delnode

end_del_obj:
        lw      $ra, ($sp)
        addi    $sp, $sp, 4
        jr      $ra

del_category:
        
        la      $t0, wclist                 
        lw      $t1, ($t0)                  
        bne     $t1, $0, del_cat_objects
        la      $a0, errorMessage
        li      $v0, 4
        syscall
        li      $a0, 401
        li      $v0, 1
        syscall
        jr      $ra

del_cat_objects:
        addi    $sp, $sp, -12
        sw      $ra, ($sp)
        sw      $t0, 4($sp)                 
        sw      $t1, 8($sp)                 
        addi    $t2, $t1, 4                 

while5:
        lw      $t7, ($t2)                  
        beq     $t7, $0, del_current_cat    
        move    $a0, $t7                    
        move    $a1, $t2                    
        jal     delnode                     
        j       while5

del_current_cat:
        
        lw      $t0, cclist                 
        lw      $t0, 12($t0)                
        sw      $t0, cclist                 

        lw      $a0, 8($sp)                 
        lw      $a1, 4($sp)                 
        jal     delnode                     
        
        lw      $t0, 4($sp)
        lw      $t1, ($t0)                  
        bne     $t1, $0, end_del_cat        
        sw      $0, cclist
        j       end_del_cat

end_del_cat:
        lw      $ra, ($sp)
        addi    $sp, $sp, 12
        jr      $ra

list_errors:
        la      $a0, errorList
        li      $v0, 4
        syscall
        jr      $ra

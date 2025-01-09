package com.DerianSepero.StockBazar.Controller;

import com.DerianSepero.StockBazar.Model.Cliente;
import com.DerianSepero.StockBazar.Service.IClienteService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    
    @Autowired
    private IClienteService clienteServ;
    
    //Creacion de un cliente
    @PostMapping("/clientes/crear")
    ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente){
        
        clienteServ.saveCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    //Ver todos los clientes
    @GetMapping("/clientes")
    ResponseEntity<List<Cliente>> verClientes(){
        List<Cliente> listaClientes = clienteServ.getClientes();
        if(listaClientes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(listaClientes);
    }
    
    //Ver cliente en particular segun su ID
    @GetMapping("/clientes/{id_cliente}")
    ResponseEntity<?> findCliente(@PathVariable Long id_cliente){
        Cliente cliente = clienteServ.findCliente(id_cliente);
        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La persona con ID " + id_cliente + " no fue encontrada");
        }
        return ResponseEntity.ok(cliente);
    }
      
    @DeleteMapping("/clientes/eliminar/{id_cliente}")
    ResponseEntity<Void> deleteCliente(@PathVariable Long id_cliente){
        Cliente cliente = clienteServ.findCliente(id_cliente);
        if(cliente == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clienteServ.deleteCliente(id_cliente);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    
    @PutMapping("/clientes/editar/{id_cliente}")
    ResponseEntity <?> editCliente(@PathVariable Long id_cliente,
            @RequestParam(required = false, name = "nombre") String nuevoNombre,
            @RequestParam(required = false, name = "apellido") String nuevoApellido,
            @RequestParam(required = false, name = "dni") String nuevoDni){
        
        Cliente cliente = clienteServ.findCliente(id_cliente);
        
        if(cliente == null){
        
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje","La persona con el ID " + id_cliente + " no fue encontrado"));
        }   
          
        clienteServ.editCliente(id_cliente, nuevoNombre, nuevoApellido, nuevoDni);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of(
                "mensaje", "La persona con ID " + id_cliente + " fue editada correctamente.",
                "cliente", cliente
            ));
    }
    
}

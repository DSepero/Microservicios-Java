package com.DerianSepero.StockBazar.Service;

import com.DerianSepero.StockBazar.Model.Cliente;
import com.DerianSepero.StockBazar.Repository.IClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepo;
    
    // Crear un cliente
    @Override
    public void saveCliente(Cliente cliente) {
        
        clienteRepo.save(cliente);
    }
    
    //Mostrar lista de clientes
    @Override
    public List<Cliente> getClientes() {
        
        List<Cliente> listaClientes = clienteRepo.findAll();
        return listaClientes;
    }

    // Mostrar cliente segun su ID
    @Override
    public Cliente findCliente(Long id_cliente) {
        Cliente cliente = clienteRepo.findById(id_cliente).orElse(null);
        return cliente;
    }

    //Eliminar cliente segun su ID
    @Override
    public void deleteCliente(Long id_cliente) {
        
       clienteRepo.deleteById(id_cliente);
    }

    //Editar cliente segun su ID
    @Override
    public void editCliente(Long id_cliente, String nuevoNombre, String nuevoApellido, String nuevodni) {
        
        Cliente cliente = this.findCliente(id_cliente);
        cliente.setNombre(nuevoNombre);
        cliente.setApellido(nuevoApellido);
        cliente.setDni(nuevodni);
        
        this.saveCliente(cliente);
        
        
    }

    
    
}

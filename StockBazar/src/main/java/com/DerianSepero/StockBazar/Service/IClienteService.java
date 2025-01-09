package com.DerianSepero.StockBazar.Service;

import com.DerianSepero.StockBazar.Model.Cliente;
import java.util.List;

public interface IClienteService {
    
    public void saveCliente (Cliente cliente);
    
    public List<Cliente> getClientes ();
    
    public Cliente findCliente (Long id_cliente);
    
    public void deleteCliente (Long id_cliente);
    
    public void editCliente (Long id_cliente, String nuevoNombre,
                            String nuevoApellido, String nuevodni);
    
}

package br.edu.umfg;

import br.edu.umfg.controller.ClienteController;
import br.edu.umfg.controller.OrdemServicoController;
import br.edu.umfg.controller.VeiculoController;
import br.edu.umfg.model.Cliente;
import br.edu.umfg.model.OrdemServico;
import br.edu.umfg.model.Veiculo;
public class Main {

    public static void main(String[] args) {

        ClienteController clienteController = new ClienteController();
        VeiculoController veiculoController = new VeiculoController();
        OrdemServicoController osController      = new OrdemServicoController();

        System.out.println("========== SIMULAÇÃO: Sistema de Oficina Mecânica ==========\n");


        System.out.println("--- 1. Cadastrar Cliente ---");
        Cliente cliente = new Cliente("Carlos Pereira", "44988776655");
        cliente = clienteController.cadastrar(cliente);


        System.out.println("\n--- 2. Cadastrar Veículo ---");
        Veiculo veiculo = new Veiculo("ayd-1470", "Honda Civic", 2020, cliente.getId());
        veiculo = veiculoController.cadastrar(veiculo);


        Veiculo veiculo2 = new Veiculo("Xyz-5226", "Toyota Corolla", 2018, cliente.getId());
        veiculo2 = veiculoController.cadastrar(veiculo2);


        System.out.println("\n--- 3. Abrir Ordem de Serviço ---");
        OrdemServico os1 = new OrdemServico(veiculo.getId(), "Troca de óleo e filtro", 250.00);
        os1 = osController.abrir(os1);

        OrdemServico os2 = new OrdemServico(veiculo.getId(), "Alinhamento e balanceamento", 180.00);
        os2 = osController.abrir(os2);


        System.out.println("\n--- 4. Concluir Ordem de Serviço ---");
        osController.concluir(os1.getId());


        System.out.println("\n--- 5. Histórico de Manutenções do Veículo ---");
        osController.listarPorVeiculo(veiculo.getId());


        System.out.println("\n--- 6. Veículos do Cliente ---");
        veiculoController.listarPorCliente(cliente.getId());


        System.out.println("\n--- 7. TESTE: OS com valor NEGATIVO (deve falhar) ---");
        OrdemServico invalida1 = new OrdemServico(veiculo.getId(), "Revisão", -100.00);
        osController.abrir(invalida1);

        System.out.println("\n--- 8. TESTE: OS para veículo INEXISTENTE (deve falhar) ---");
        OrdemServico invalida2 = new OrdemServico(9999, "Revisão", 200.00);
        osController.abrir(invalida2);

        System.out.println("\n========== FIM DA SIMULAÇÃO ==========");
    }
}
package br.edu.umfg;

import br.edu.umfg.controller.AnimalController;
import br.edu.umfg.controller.ConsultaController;
import br.edu.umfg.controller.TutorController;
import br.edu.umfg.model.Animal;
import br.edu.umfg.model.Consulta;
import br.edu.umfg.model.Tutor;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TutorController tutorController = new TutorController();
        AnimalController animalController = new AnimalController();
        ConsultaController consultaController = new ConsultaController();

        // 1. Cadastrar o tutor
        System.out.println("====> CADASTRANDO TUTOR <====");
        Tutor tutor = new Tutor("Diego Souza", "Rua de Japurá, 777", "4499959012");
        tutorController.cadastrar(tutor);
        System.out.println("Tutor ID gerado: " + tutor.getId());

        // 2. Cadastrar o animal vinculado ao tutor
        System.out.println("\n====> CADASTRANDO ANIMAL <====");
        Animal animal = new Animal("Diego Jr", "Mosquito", "Dengue", tutor.getId());
        animalController.cadastrar(animal);
        System.out.println("Animal ID gerado: " + animal.getId());

        // 3. Registrar a consulta vinculada ao animal
        System.out.println("\n====> REGISTRANDO CONSULTA <====");
        Consulta consulta = new Consulta(animal.getId(), LocalDate.now(), "Vacina Zika Virus", 150.00);
        consultaController.cadastrar(consulta);
        System.out.println("Consulta ID gerado: " + consulta.getId());

        // 4. Listar os animais do tutor
        System.out.println("\n====> ANIMAIS DO TUTOR <====");
        List<Animal> animaisDoTutor = animalController.listarPorTutor(tutor.getId());
        animaisDoTutor.forEach(System.out::println);

        // 5. Listar as consultas do animal
        System.out.println("\n====> HISTÓRICO DE CONSULTAS DO ANIMAL <====");
        List<Consulta> consultasDoAnimal = consultaController.listarPorAnimal(animal.getId());
        consultasDoAnimal.forEach(System.out::println);
    }
}
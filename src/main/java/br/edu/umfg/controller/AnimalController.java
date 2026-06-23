package br.edu.umfg.controller;
import br.edu.umfg.model.Animal;
import br.edu.umfg.service.AnimalService;

import java.util.List;

public class AnimalController {

    private AnimalService animalService = new AnimalService();

    public void cadastrar(Animal animal) {
        animalService.cadastrar(animal);
        System.out.println("Animal cadastrado com sucesso: " + animal);
    }

    public List<Animal> listarTodos() {
        return animalService.listarTodos();
    }

    public Animal buscarPorId(int id) {
        return animalService.buscarPorId(id);
    }

    public List<Animal> listarPorTutor(int idTutor) {
        return animalService.listarPorTutor(idTutor);
    }

    public void atualizar(Animal animal) {
        animalService.atualizar(animal);
        System.out.println("Animal atualizado com sucesso: " + animal);
    }

    public void deletar(int id) {
        animalService.deletar(id);
        System.out.println("Animal deletado com sucesso! ID: " + id);
    }
}
package br.edu.umfg.service;
import br.edu.umfg.model.Animal;
import br.edu.umfg.repository.AnimalRepository;
import br.edu.umfg.repository.TutorRepository;

import java.util.List;

public class AnimalService {

    private AnimalRepository animalRepository = new AnimalRepository();
    private TutorRepository tutorRepository = new TutorRepository();

    public void cadastrar(Animal animal) {
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new RuntimeException("Nome do animal não pode ser vazio!");
        }
        if (animal.getEspecie() == null || animal.getEspecie().isBlank()) {
            throw new RuntimeException("Espécie do animal não pode ser vazia!");
        }
        if (tutorRepository.buscarPorId(animal.getIdTutor()) == null) {
            throw new RuntimeException("Tutor não encontrado!");
        }
        animalRepository.salvar(animal);
    }

    public List<Animal> listarTodos() {
        return animalRepository.listarTodos();
    }

    public Animal buscarPorId(int id) {
        Animal animal = animalRepository.buscarPorId(id);
        if (animal == null) {
            throw new RuntimeException("Animal não encontrado!");
        }
        return animal;
    }

    public List<Animal> listarPorTutor(int idTutor) {
        return animalRepository.listarPorTutor(idTutor);
    }

    public void atualizar(Animal animal) {
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new RuntimeException("Nome do animal não pode ser vazio!");
        }
        animalRepository.atualizar(animal);
    }

    public void deletar(int id) {
        animalRepository.deletar(id);
    }
}
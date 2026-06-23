package br.edu.umfg.controller;

import br.edu.umfg.model.Tutor;
import br.edu.umfg.service.TutorService;

import java.util.List;

public class TutorController {

    private TutorService tutorService = new TutorService();

    public void cadastrar(Tutor tutor) {
        tutorService.cadastrar(tutor);
        System.out.println("Tutor cadastrado com sucesso: " + tutor);
    }

    public List<Tutor> listarTodos() {
        return tutorService.listarTodos();
    }

    public Tutor buscarPorId(int id) {
        return tutorService.buscarPorId(id);
    }

    public void atualizar(Tutor tutor) {
        tutorService.atualizar(tutor);
        System.out.println("Tutor atualizado com sucesso: " + tutor);
    }

    public void deletar(int id) {
        tutorService.deletar(id);
        System.out.println("Tutor deletado com sucesso! ID: " + id);
    }
}
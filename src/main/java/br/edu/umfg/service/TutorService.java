package br.edu.umfg.service;
import br.edu.umfg.model.Tutor;
import br.edu.umfg.repository.TutorRepository;
import java.util.List;

public class TutorService {

    private TutorRepository tutorRepository = new TutorRepository();

    public void cadastrar(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank()) {
            throw new RuntimeException("Nome do tutor não pode ser vazio!");
        }
        if (tutor.getTelefone() == null || tutor.getTelefone().isBlank()) {
            throw new RuntimeException("Telefone do tutor não pode ser vazio!");
        }
        tutorRepository.salvar(tutor);
    }

    public List<Tutor> listarTodos() {
        return tutorRepository.listarTodos();
    }

    public Tutor buscarPorId(int id) {
        Tutor tutor = tutorRepository.buscarPorId(id);
        if (tutor == null) {
            throw new RuntimeException("Tutor não encontrado!");
        }
        return tutor;
    }

    public void atualizar(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank()) {
            throw new RuntimeException("Nome do tutor não pode ser vazio!");
        }
        tutorRepository.atualizar(tutor);
    }

    public void deletar(int id) {
        tutorRepository.deletar(id);
    }
}
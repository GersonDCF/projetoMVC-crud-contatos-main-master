package sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sistema.model.Contato;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomeController {

    @Autowired
    JdbcTemplate db;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/contatos")
    public String contatos(Model model) {
        List<Contato> listaDeContatos = db.query(
                "select * from contatos",
                (res, rowNum) -> {
                    Contato contato = new Contato(
                            res.getInt("id"),
                            res.getString("nome"));
                    return contato;
                });
        model.addAttribute("contatos", listaDeContatos);
        return "contato";
    }

    @GetMapping("novo")
    public String exibeForm(Model model) {
        model.addAttribute("contato", new Contato());
        return "formulario";
    }

    @PostMapping("novo")
    public String gravaDados(Contato contato) {
        db.update("insert into contatos(nome) values (?)",
                contato.getNome());
        return "home";
    }

    @GetMapping("excluir-contato")
    public String apagarContato(@RequestParam(value = "id", required = true) Integer cod) {
        db.update("delete from contatos where id = ?", cod);
        return "redirect:/contatos";
    }

    @GetMapping("editar-contato")
    public String mostraFormAlteraContato(@RequestParam(value = "id", required = true) Integer cod, Model model) {
        System.out.println("--------------------> " + cod);
        Contato contato = db.queryForObject(
                "select * from contatos where id = ?",
                (rs, rowNum) -> {
                    Contato c = new Contato();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    return c;
                },
                cod);
        model.addAttribute("contat", contato);
        return "formeditacontato";
    }

    @PostMapping("gravacontatomodificado")
    public String gravaContatoModificado(Contato contato) {
        db.update(
                "update contatos set nome=? where id = ?",
                contato.getNome(),
                contato.getId());
        return "redirect:/contatos";
    }

}
package sistema.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contato {
    private int id;
    private String nome;

    public Contato(int id, String nome) {
        this.id = id;
        this.nome = nome;        
    }
}

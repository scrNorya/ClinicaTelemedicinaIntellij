package org.example.utils;
import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Medico;
import org.example.model.Pessoa;
import org.example.model.Recepcionista;
public class Json {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();


    /*
     * Instancia o ObjectMapper; Colocado dentro de um metodo pois pode ser
     * necessario adicionar configuracoes no objectMapper ao decorrer do projeto;
     */
    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    public static Map<String, Object> readValues(Persons person) throws URISyntaxException, IOException {
        BufferedReader br;

        br = new BufferedReader(new FileReader(person.getPath()));

        return objectMapper.readValue(br, Map.class);
    }

    public static void writeValue(Recepcionista recepcionista) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(Persons.Recepcionista);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", recepcionista.getCpf());
        values.put("nome", recepcionista.getNome());
        values.put("email", recepcionista.getEmail());
        values.put("endereco", recepcionista.getEndereco());
        values.put("telefone", recepcionista.getTelefone());
        values.put("senha", recepcionista.getSenha());

        json.put(UUID.randomUUID().toString(), values);
        objectMapper.writeValue(new File(Persons.Recepcionista.getPath()), json);
    }

    public static void writeValue(Medico medico) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(Persons.Medico);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", medico.getCpf());
        values.put("nome", medico.getNome());
        values.put("CRM", medico.getCrm());
        values.put("email", medico.getEmail());
        values.put("endereco", medico.getEndereco());
        values.put("telefone", medico.getTelefone());
        values.put("senha", medico.getSenha());

        json.put(String.valueOf(UUID.randomUUID().toString()), values);
        objectMapper.writeValue(new File(Persons.Medico.getPath()), json);
    }

    public static void updateValue(Recepcionista recepcionista, String key) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(Persons.Recepcionista);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", recepcionista.getCpf());
        values.put("nome", recepcionista.getNome());
        values.put("email", recepcionista.getEmail());
        values.put("endereco", recepcionista.getEndereco());
        values.put("telefone", recepcionista.getTelefone());
        values.put("senha", recepcionista.getSenha());

        json.replace(key, values);
        objectMapper.writeValue(new File(Persons.Recepcionista.getPath()), json);
    }

    public static void updateValue(Medico medico, String key) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(Persons.Recepcionista);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", medico.getCpf());
        values.put("nome", medico.getNome());
        values.put("CRM", medico.getCrm());
        values.put("email", medico.getEmail());
        values.put("endereco", medico.getEndereco());
        values.put("telefone", medico.getTelefone());
        values.put("senha", medico.getSenha());

        json.replace(key, values);
        objectMapper.writeValue(new File(Persons.Medico.getPath()), json);
    }

    public static void deleteValue(String key, Persons personType) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(personType);
        json.remove(key);
        if (personType == Persons.Recepcionista) {
            objectMapper.writeValue(new File(Persons.Recepcionista.getPath()), json);
        } else if(personType == Persons.Medico) {
            objectMapper.writeValue(new File(Persons.Medico.getPath()), json);
        }
    }

    public static Pessoa findByCPF(String cpf, Persons personType) throws Exception {
        Pessoa pessoa;

        if (Validations.isCpf(cpf)) {
            Map<String, Object> json = readValues(personType);

            if (personType == Persons.Recepcionista) {
                for (Map.Entry<String, Object> entry : json.entrySet()) {
                    Map<String, Object> values = (Map<String, Object>) entry.getValue();
                    if (cpf.equals(values.get("cpf"))) {
                        long telefoneValue = Long.parseLong(values.get("telefone").toString());
                        pessoa = new Recepcionista(values.get("nome").toString(), cpf, telefoneValue,
                                values.get("email").toString(), values.get("endereco").toString(),
                                values.get("senha").toString());
                        return pessoa;
                    }
                }
            } else if (personType == Persons.Medico) {
                for (Map.Entry<String, Object> entry : json.entrySet()) {
                    Map<String, Object> values = (Map<String, Object>) entry.getValue();
                    if (cpf.equals(values.get("cpf"))) {
                        long telefoneValue = Long.parseLong(values.get("telefone").toString());
                        long CRMValue = Long.parseLong(values.get("CRM").toString());
                        pessoa = new Medico(values.get("nome").toString(), cpf, CRMValue ,telefoneValue,
                                values.get("email").toString(), values.get("endereco").toString(),
                                values.get("senha").toString());
                        return pessoa;
                    }
                }
            }
        }
        return null;
    }

    public static Map.Entry<String, Object> findEntryByCpf(String cpf, Persons personType) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(personType);
        Map.Entry<String, Object> entry = null;
        Map<String, Object> values;

        if(json!=null) {
            for (Map.Entry<String, Object> e : json.entrySet()) {
                values = (Map<String, Object>) e.getValue();
                if (cpf.equals(values.get("cpf"))) {
                    entry = e;

                }
            }
        }
        return entry;
    }

    public static Pessoa parseEntryToPessoa(Map.Entry<String, Object> pessoaEntry, Persons personType) {
        Pessoa pessoa = null;
        Map <String, Object> pessoaMap = (Map <String, Object>) pessoaEntry.getValue();
        if(personType == Persons.Recepcionista) {
            pessoa = new Recepcionista(pessoaMap.get("nome").toString(), pessoaMap.get("cpf").toString(),
                    Long.parseLong(pessoaMap.get("telefone").toString()), pessoaMap.get("email").toString(),
                    pessoaMap.get("endereco").toString(), pessoaMap.get("senha").toString());
        } else if (personType == Persons.Medico) {
            pessoa = new Medico(pessoaMap.get("nome").toString(), pessoaMap.get("cpf").toString(),
                    Long.parseLong(pessoaMap.get("CRM").toString()),
                    Long.parseLong(pessoaMap.get("telefone").toString()), pessoaMap.get("email").toString(),
                    pessoaMap.get("endereco").toString(), pessoaMap.get("senha").toString());
        }

        return pessoa;
    }
}

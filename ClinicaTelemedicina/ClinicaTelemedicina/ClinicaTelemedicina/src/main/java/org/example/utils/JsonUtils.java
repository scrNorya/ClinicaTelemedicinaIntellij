package org.example.utils;
import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Medico;
import org.example.model.Paciente;
import org.example.model.Pessoa;
import org.example.model.Recepcionista;
public class JsonUtils {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();


    /*
     * Instancia o ObjectMapper; Colocado dentro de um metodo pois pode ser
     * necessario adicionar configuracoes no objectMapper ao decorrer do projeto;
     */
    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        return defaultObjectMapper;
    }

    public static Map<String, Object> readValues(JsonType person) throws URISyntaxException, IOException {
        BufferedReader br;

        br = new BufferedReader(new FileReader(person.getPath()));

        return objectMapper.readValue(br, Map.class);
    }

    public static void writeValue(Paciente paciente) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(JsonType.Paciente);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", paciente.getCpf());
        values.put("nome", paciente.getNome());
        values.put("email", paciente.getEmail());
        values.put("endereco", paciente.getEndereco());
        values.put("telefone", paciente.getTelefone());

        json.put(UUID.randomUUID().toString(), values);
        objectMapper.writeValue(new File(JsonType.Paciente.getPath()), json);
    }

    public static void writeValue(Recepcionista recepcionista) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(JsonType.Recepcionista);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", recepcionista.getCpf());
        values.put("nome", recepcionista.getNome());
        values.put("email", recepcionista.getEmail());
        values.put("endereco", recepcionista.getEndereco());
        values.put("telefone", recepcionista.getTelefone());
        values.put("senha", recepcionista.getSenha());

        json.put(UUID.randomUUID().toString(), values);
        objectMapper.writeValue(new File(JsonType.Recepcionista.getPath()), json);
    }


    public static void writeValue(Medico medico) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(JsonType.Medico);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", medico.getCpf());
        values.put("nome", medico.getNome());
        values.put("CRM", medico.getCrm());
        values.put("email", medico.getEmail());
        values.put("endereco", medico.getEndereco());
        values.put("telefone", medico.getTelefone());
        values.put("senha", medico.getSenha());

        json.put(String.valueOf(UUID.randomUUID().toString()), values);
        objectMapper.writeValue(new File(JsonType.Medico.getPath()), json);
    }

    public static void updateValue(Paciente paciente, String key) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(JsonType.Recepcionista);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", paciente.getCpf());
        values.put("nome", paciente.getNome());
        values.put("email", paciente.getEmail());
        values.put("endereco", paciente.getEndereco());
        values.put("telefone", paciente.getTelefone());

        json.replace(key, values);
        objectMapper.writeValue(new File(JsonType.Recepcionista.getPath()), json);
    }

    public static void updateValue(Recepcionista recepcionista, String key) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(JsonType.Recepcionista);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", recepcionista.getCpf());
        values.put("nome", recepcionista.getNome());
        values.put("email", recepcionista.getEmail());
        values.put("endereco", recepcionista.getEndereco());
        values.put("telefone", recepcionista.getTelefone());
        values.put("senha", recepcionista.getSenha());

        json.replace(key, values);
        objectMapper.writeValue(new File(JsonType.Recepcionista.getPath()), json);
    }

    public static void updateValue(Medico medico, String key) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(JsonType.Recepcionista);
        Map<String, Object> values = new HashMap<>();

        values.put("cpf", medico.getCpf());
        values.put("nome", medico.getNome());
        values.put("CRM", medico.getCrm());
        values.put("email", medico.getEmail());
        values.put("endereco", medico.getEndereco());
        values.put("telefone", medico.getTelefone());
        values.put("senha", medico.getSenha());

        json.replace(key, values);
        objectMapper.writeValue(new File(JsonType.Medico.getPath()), json);
    }

    public static void deleteValue(String key, JsonType jsonType) throws URISyntaxException, IOException {
        Map<String, Object> json = readValues(jsonType);
        json.remove(key);
        objectMapper.writeValue(new File(jsonType.getPath()), json);
    }

    public static Pessoa findByCPF(String cpf, JsonType jsonType) throws Exception {
        Pessoa pessoa;

        if (ValidationUtils.isCpf(cpf)) {
            Map<String, Object> json = readValues(jsonType);

            if (jsonType == JsonType.Recepcionista) {
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
            } else if (jsonType == JsonType.Medico) {
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
            } else {
                for (Map.Entry<String, Object> entry : json.entrySet()) {
                    Map<String, Object> values = (Map<String, Object>) entry.getValue();
                    if (cpf.equals(values.get("cpf"))) {
                        long telefoneValue = Long.parseLong(values.get("telefone").toString());
                        pessoa = new Paciente(values.get("nome").toString(), cpf, telefoneValue,
                                values.get("email").toString(), values.get("endereco").toString(), null);
                        //TODO pessoa consultas
                        return pessoa;
                    }
                }
            }
        }
        return null;
    }

    public static Map.Entry<String, Object> findEntryByCpf(String cpf, JsonType jsonType) throws Exception {
        if(ValidationUtils.isCpf(cpf)) {
            Map<String, Object> json = readValues(jsonType);
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
        return null;
    }
}

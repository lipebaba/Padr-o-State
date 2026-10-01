# Padrão State

Projeto desenvolvido para demonstrar a utilização do padrão de projeto **State** em Java.

## 📌 Sobre o projeto

O projeto simula o funcionamento de um **reprodutor de áudio**, cujo comportamento muda de acordo com seu estado atual.

O padrão State é utilizado para representar os diferentes estados do reprodutor e definir quais operações podem ser realizadas em cada um deles, permitindo que as regras de transição fiquem distribuídas entre as próprias classes de estado.

Neste projeto, o reprodutor pode estar em três estados:

- **Parado**
- **Reproduzindo**
- **Pausado**

O usuário pode executar três comandos:

- Reproduzir.
- Pausar.
- Parar.

Cada comando pode ser aceito ou recusado dependendo do estado atual do reprodutor.

## 🧩 Padrão State

O **State** é um padrão de projeto comportamental que permite que um objeto altere seu comportamento quando seu estado interno muda.

Em vez de concentrar todas as regras de comportamento e transição em uma única classe utilizando várias estruturas condicionais, cada estado é representado por uma classe própria.

Neste projeto, a classe `Reprodutor` representa o contexto e mantém uma referência para um objeto da classe abstrata `ReprodutorEstado`.

A classe abstrata `ReprodutorEstado` define as operações disponíveis:

```java
reproduzir(Reprodutor reprodutor)
pausar(Reprodutor reprodutor)
parar(Reprodutor reprodutor)
```

Os estados concretos são representados pelas classes:

- `ReprodutorEstadoParado`
- `ReprodutorEstadoReproduzindo`
- `ReprodutorEstadoPausado`

Dessa forma:

```yaml
Reprodutor
    └── ReprodutorEstado
          ├── ReprodutorEstadoParado
          ├── ReprodutorEstadoReproduzindo
          └── ReprodutorEstadoPausado

Parado
    └── reproduzir → Reproduzindo

Reproduzindo
    ├── pausar → Pausado
    └── parar → Parado

Pausado
    ├── reproduzir → Reproduzindo
    └── parar → Parado
```

O `Reprodutor` delega as operações ao objeto que representa seu estado atual. Assim, são as próprias classes de estado que determinam se uma operação é permitida e, quando necessário, realizam a transição para outro estado.

## 📁 Estrutura do projeto

```text
Padr-o-State/
│
├── docs/
│   └── diagrama-classes.png
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── padroescomportamentais/
│   │           └── state/
│   │               ├── Aplicacao.java
│   │               ├── Reprodutor.java
│   │               ├── ReprodutorEstado.java
│   │               ├── ReprodutorEstadoParado.java
│   │               ├── ReprodutorEstadoPausado.java
│   │               └── ReprodutorEstadoReproduzindo.java
│   │
│   └── test/
│       └── java/
│           └── padroescomportamentais/
│               └── state/
│                   └── ReprodutorTest.java
│
├── .gitignore
├── pom.xml
└── README.md
```

## ⚙️ Funcionamento

A classe `Reprodutor` mantém uma referência para seu estado atual:

```java
private ReprodutorEstado estado;
```

Quando um novo reprodutor é criado, ele inicia automaticamente no estado **Parado**.

Os principais métodos disponibilizados pelo reprodutor são:

| Método | Responsabilidade |
| --- | --- |
| `reproduzir()` | Solicitar ao estado atual o início ou retomada da reprodução. |
| `pausar()` | Solicitar ao estado atual a pausa da reprodução. |
| `parar()` | Solicitar ao estado atual a interrupção da reprodução. |
| `getEstado()` | Retornar o objeto que representa o estado atual. |
| `getNomeEstado()` | Retornar o nome do estado atual. |
| `setEstado(ReprodutorEstado estado)` | Alterar internamente o estado do reprodutor. |

Os métodos `reproduzir()`, `pausar()` e `parar()` não implementam diretamente as regras de transição. Eles delegam a decisão para o estado atual.

Por exemplo:

```java
public boolean reproduzir() {
    return estado.reproduzir(this);
}
```

### Transições de estado

As transições implementadas no projeto são:

| Estado atual | Comando | Resultado | Novo estado |
| --- | --- | --- | --- |
| Parado | Reproduzir | Aceito | Reproduzindo |
| Parado | Pausar | Recusado | Parado |
| Parado | Parar | Recusado | Parado |
| Reproduzindo | Reproduzir | Recusado | Reproduzindo |
| Reproduzindo | Pausar | Aceito | Pausado |
| Reproduzindo | Parar | Aceito | Parado |
| Pausado | Reproduzir | Aceito | Reproduzindo |
| Pausado | Pausar | Recusado | Pausado |
| Pausado | Parar | Aceito | Parado |

Quando uma operação não é permitida, o método retorna `false` e o estado atual é mantido.

Quando uma operação é permitida, o método altera o estado do reprodutor e retorna `true`.

### Estado Parado

A classe `ReprodutorEstadoParado` permite apenas a operação de reprodução.

Ao executar:

```java
reprodutor.reproduzir();
```

o estado é alterado de:

```text
Parado → Reproduzindo
```

As operações `pausar()` e `parar()` são recusadas.

### Estado Reproduzindo

A classe `ReprodutorEstadoReproduzindo` permite duas operações:

```text
Pausar → Pausado
Parar  → Parado
```

Executar `reproduzir()` novamente enquanto o reprodutor já está reproduzindo é uma operação recusada.

### Estado Pausado

A classe `ReprodutorEstadoPausado` permite:

```text
Reproduzir → Reproduzindo
Parar      → Parado
```

Executar `pausar()` novamente é uma operação recusada.

## ♻️ Instâncias dos estados

Os três estados concretos possuem apenas uma instância compartilhada.

Por exemplo, a classe `ReprodutorEstadoParado` possui:

```java
private static final ReprodutorEstadoParado instance =
        new ReprodutorEstadoParado();
```

e disponibiliza essa instância através de:

```java
public static ReprodutorEstadoParado getInstance()
```

O mesmo acontece com os estados `ReprodutorEstadoReproduzindo` e `ReprodutorEstadoPausado`.

Como os objetos de estado não armazenam informações específicas de um determinado reprodutor, suas instâncias podem ser compartilhadas entre vários objetos `Reprodutor`.

Mesmo compartilhando os mesmos objetos de estado, cada reprodutor mantém sua própria referência para o estado atual. Portanto, alterar o estado de um reprodutor não altera o estado de outro.

## 🛡️ Validação do estado

O método responsável pela alteração do estado utiliza `Objects.requireNonNull`:

```java
void setEstado(ReprodutorEstado estado) {
    this.estado = Objects.requireNonNull(estado, "Estado obrigatório");
}
```

Dessa forma, uma tentativa de definir um estado `null` gera uma `NullPointerException` com a mensagem:

```text
Estado obrigatório
```

O estado anterior do reprodutor é preservado caso essa tentativa ocorra.

## ▶️ Executando a aplicação

Com o **JDK 11 ou superior** e o **Maven** instalados, execute na pasta do projeto:

```bash
mvn compile
java -cp target/classes padroescomportamentais.state.Aplicacao
```

A classe `Aplicacao` demonstra uma sequência de operações sobre o reprodutor:

```text
Estado inicial: Parado

Pausar
Reproduzir
Pausar
Retomar
Parar
```

O programa informa se cada comando foi aceito ou recusado e apresenta o estado resultante.

A execução segue o seguinte fluxo:

```text
Parado
  │
  ├── Pausar → recusado
  │
  └── Reproduzir → Reproduzindo
                       │
                       └── Pausar → Pausado
                                        │
                                        └── Reproduzir → Reproduzindo
                                                               │
                                                               └── Parar → Parado
```

## 🏗️ Estrutura do State

Os elementos do padrão utilizados no projeto podem ser identificados da seguinte forma:

| Elemento do State | Implementação |
| --- | --- |
| Context — objeto que possui estado | `Reprodutor` |
| State — definição dos comportamentos | `ReprodutorEstado` |
| Concrete State | `ReprodutorEstadoParado` |
| Concrete State | `ReprodutorEstadoReproduzindo` |
| Concrete State | `ReprodutorEstadoPausado` |
| Client — demonstração | `Aplicacao` |

A classe `Reprodutor` não precisa conhecer detalhadamente as regras de cada estado.

Ela apenas delega as operações:

```java
estado.reproduzir(this);
estado.pausar(this);
estado.parar(this);
```

Cada estado concreto é responsável por decidir quais operações são válidas e realizar as transições correspondentes.

Essa organização evita concentrar toda a lógica em grandes estruturas condicionais e facilita a inclusão de novos estados e comportamentos.

## 🧪 Testes

O projeto possui testes automatizados utilizando **JUnit 5**.

A classe `ReprodutorTest` verifica:

- Se um novo reprodutor inicia no estado `Parado`.
- Todas as combinações entre os três estados e os três comandos.
- A transição de `Parado` para `Reproduzindo`.
- A impossibilidade de pausar ou parar quando o reprodutor já está parado.
- A transição de `Reproduzindo` para `Pausado`.
- A transição de `Reproduzindo` para `Parado`.
- A impossibilidade de executar novamente `reproduzir()` enquanto já está reproduzindo.
- A transição de `Pausado` para `Reproduzindo`.
- A transição de `Pausado` para `Parado`.
- A impossibilidade de pausar novamente quando o reprodutor já está pausado.
- Um ciclo completo de reprodução, pausa, retomada e parada.
- A independência entre diferentes objetos `Reprodutor`.
- O compartilhamento das instâncias dos objetos de estado.
- A rejeição de um estado `null`.
- A preservação do estado atual quando uma operação é recusada.

Os testes das transições utilizam **testes dinâmicos do JUnit**, permitindo verificar de forma organizada todas as combinações entre estados e comandos.

Para executar os testes utilizando Maven:

```bash
mvn test
```

## 📊 Diagrama de Classes

O diagrama de classes do projeto está disponível na pasta `docs`.

Ele apresenta a relação entre a classe `Reprodutor`, a classe abstrata `ReprodutorEstado` e os três estados concretos.

[Diagrama de classes do padrão State](https://github.com/lipebaba/Padr-o-State/blob/main/docs/diagrama-classes.png)

![Diagrama de classes do padrão State](https://github.com/lipebaba/Padr-o-State/raw/main/docs/diagrama-classes.png)

## 🛠️ Tecnologias utilizadas

- Java 11
- Maven
- JUnit 5.5.2
- Padrões de Projeto — State

## 🎯 Objetivo

O objetivo deste projeto é demonstrar de forma prática a aplicação do padrão State, permitindo que o comportamento de um reprodutor de áudio seja determinado pelo seu estado atual.

Com essa abordagem, as regras de cada estado ficam encapsuladas em classes específicas, evitando que a classe `Reprodutor` concentre todas as decisões em estruturas condicionais.

A implementação também demonstra como objetos de estado sem informações próprias podem ser compartilhados entre diferentes contextos, mantendo cada reprodutor independente.

Novos estados ou comportamentos podem ser adicionados por meio de novas subclasses de `ReprodutorEstado`, mantendo a organização e reduzindo o acoplamento da lógica de transição.

## 👨‍💻 Autor

**Felipe Baba**

Projeto desenvolvido para fins acadêmicos, como aplicação prática do padrão de projeto State.
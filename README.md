# IOT-Broker

" Neste problema, você faz parte de uma _startup_ na área de IoT que acabou de ser
adquirida por uma empresa que desenvolve sensores e dispositivos para variados
setores da indústria. Embora essa empresa venha adicionando capacidades de rede 
(TCP/IP) aos dispositivos que ela produz, ainda não sabe como fazer a comunicação
e integração entre esses dispositivos e com suas aplicações.  

 A motivação para aquisição de sua startup por essa empresa foi exatamente
o _know-how_ de sua equipe no desenvolvimento de middleware distribuído. 
Dessa forma, assim que a aquisição da sua startup foi completada, sua equipe 
foi direcionada para o desenvolvimento de um serviço que permita facilitar a 
comunicação entre os diferentes dispositivos da empresa e as aplicações que 
precisam dos dados gerados por esses dispositivos e ou comandar seu comportamento.  

 O sistema deve ser implementado através de um serviço `broker` que permita a 
troca de mensagens entre clientes (dispositivos e aplicações), utilizando como 
base o subsistema de rede `TCP/IP`. Através desse serviço, um dispositivo ou 
aplicativo deve ser capaz de enviar e ou receber mensagens para outros 
dispositivos ou aplicações interessadas. Os clientes deste serviço devem também
ser capazes de filtrar para quais outros clientes (dispositivos/aplicações) 
podem enviar e receber mensagens. Assim, os diferentes dispositivos e aplicações 
podem se comunicar e trabalhar para uma comunicação cooperativa entre as partes."

## Produto

- Um protocolo RESTful sobre o serviço implementado que permita a troca de dados
e comando um sensor e uma aplicação;

- Uma aplicação simples com interface gráfica que permita a seleção de um dispositivo
o seu controle (ligar, reiniciar, desligar, alterar parâmetros, etc.) e a visualização
dos dados gerados pelo dispositivo selecionado;

- Os dispositivos (Sensores e atuadores), que podem ser simulados através de software
para geração de dados ficícios. Esses dispositivos virtuais devem possuir uma interface 
gráfica para definir a geração dos dados em tempo real. Por exemplo, se for um 
sensor de temperatura, uma caixa de entrada pode definir a temperatura inicial 
com botões laterais para facilitar a sua alteração, aumento ou diminuição. 
O mesmo pode ser aplicado para parâmetros de controle (ligar, pausar, desligar, etc.).

## Projetos

| Nome do projeto | Descrição | Status |
| --------------- | --------- | :------: |
| Broker | Aplicação responsável por gerenciar os tópicos, inscritos e publicadores. | :white_check_mark: |
| Publisher  | Aplicação responsável por gerar valores aleatórios entre 0 e 100 e enviar para o broker. | :white_check_mark: |
| Subscriber  | Aplicação capaz de acessar a lista de tópicos e consumir os dados gerados pelos publicadores. | :white_check_mark: |
| Device | Deveria ser uma aplicação capaz de simular um dispositivo publicador e assinante. Mas não foi finalizada.| :red_circle: |

### Publisher
![Aplicação publicadora gerando dados.](https://github.com/UellingtonDamasceno/IOT-Broker/blob/master/res/gifs/pub.gif)
| [Mais detalhes](https://github.com/UellingtonDamasceno/IOT-Broker/tree/master/Publisher) |
| :---------: |

### Subscriber
![Visualização do gráfico de um tópico.](https://github.com/UellingtonDamasceno/IOT-Broker/blob/master/res/gifs/graph-sub.gif)
| [Mais detalhes](https://github.com/UellingtonDamasceno/IOT-Broker/tree/master/Subscriber) |
| :---------: |

### Broker
![Log de mensagens do broker](https://github.com/UellingtonDamasceno/IOT-Broker/blob/master/res/gifs/broker.gif)
| [Mais detalhes](https://github.com/UellingtonDamasceno/IOT-Broker/tree/master/Broker) |
| :---------: |


## Objetivo de aprendizagem
- ServerSocket.
- Socket.
- TCP/IP.
- Thread.
- IoT
  - Broker.
  - Publisher.
  - Subscriber.
  
## Curiosidades
- O primeiro teste de comunicação entre computadores diferentes não funcionou por que o firewall do meu computador estava ativo. 
- Graças ao meu desempenho nesse PBL consegui minha segunda bolsa de IC.
- Esse foi o segundo contato que tive com `thread`. O primeiro foi em um projeto `c` no qual queria colocar um relógio do lado de um menu.


---------

| :arrow_left: [Problema anterior](https://github.com/UellingtonDamasceno/MyBook) |............................... :arrow_up: [Voltar ao topo](#IOT-Broker) :arrow_up: ...............................| [Próximo problema](https://github.com/UellingtonDamasceno/AutonomoCar) :arrow_right: | 
| :----: |-----| :-----:| 

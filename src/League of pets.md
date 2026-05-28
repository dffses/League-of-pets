--League of Pets--

La idea principal para nuestro juego fue basada en el juego antiguo popular llamado POU,
el cual modificamos para poder hacer un estilo propio del juego.

En nuestro caso utilizamos animales, en este caso 3 (Perro, Gato y Cocodrilo), a los cuales añadimos estadisticas iniciales  y el como con las distintos apartados de nuestro juego varian segun el animal que decidas seleccionar.

Hemos implementado los siguientes apartados: 

- Tienda de comida
- Juegos
- Reinicio
- Paseo

Cada uno de ellos con sus opciones los cuales hacen efecto en las estadisticas del personaje. A excepcion del de reinicio, este el cual sirve para reiniciar el juego asi perdiendo el personaje creado junto a sus estadisticas.

Nuestro trabajo esta organizado en distintos paquetes los cuales empiezan con nuestro apartado de ventanas, guardados en un package.

Dentro de este tenemos nuetro menu principal donde habra que iniciar el juego, dando lugar lego al menu de selecion de personaje y a su vez este dando lugar a la interfaz del juego donde estaria toda la magia de esta.

Despues tendriamos el package animal donde Guardamos la calse madre Animal de donde heredan los metodos nuestros personajes.

Y por ultimo el apartado de juegos, los cuales son una seleccion de 4 minijuegos de los cuales podran obtener puntos nuestros personajes para poder hacer sinergia con la tienda que tenemos implementada.
Estos minijuegos son de programacion sencilla ya que queriamos sumergirnos mas en el juego principal.

Ahora hablare de las clases que hemos aprendido que serian las siguientes.

- JPanel y JFrame: ambas sirven para darle un entorno grafico a java de forma sencilla, JPanel sirviendo como el contenido de la pantalla donde se define lo que quieres que apareza, en cambio Jframe es el contenedor que contiene todo el lienzo del JFrame y asi no pudiendo existir uno sin el otro.
- KeyListener: es una interfaz la cual sirve para implementar las teclas de forma interactica en los programas, tiene tres programas a implementar, unos para al presionar la tecla, otro al solar y otro al escribir el caracter de la tecla.

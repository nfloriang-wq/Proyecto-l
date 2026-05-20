package Dominio;

public class Main {

    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        Libro libro1 = new Libro(
                "L001",
                "Clean Code",
                "Robert C. Martin",
                464);

        Libro libro2 = new Libro(
                "L002",
                "Introduction to Algorithms",
                "Thomas H. Cormen",
                1312);

        Revista revista1 = new Revista(
                "R001",
                "National Geographic",
                "National Geographic Society",
                202);

        Revista revista2 = new Revista(
                "R002",
                "PC World",
                "Foundry",
                58);

        Usuario usuario1 = new Usuario(
                "Emanuel Lopez",
                "2025001",
                "emanuel@gmail.com");

        Usuario usuario2 = new Usuario(
                "Oscar Ramirez",
                "2025002",
                "oscar@gmail.com");

        biblioteca.agregarMaterial(libro1);
        biblioteca.agregarMaterial(libro2);
        biblioteca.agregarMaterial(revista1);
        biblioteca.agregarMaterial(revista2);

        biblioteca.agregarUsuario(usuario1);
        biblioteca.agregarUsuario(usuario2);

        Prestamo prestamo1 = new Prestamo(
                usuario1,
                libro1,
                "19/05/2026");

        Prestamo prestamo2 = new Prestamo(
                usuario2,
                revista1,
                "20/05/2026");

        biblioteca.agregarPrestamo(prestamo1);
        biblioteca.agregarPrestamo(prestamo2);

        System.out.println("===== LISTA DE MATERIALES =====");

        for (Material material : biblioteca.getMateriales()) {

            System.out.println("Codigo: " + material.getCodigo());
            System.out.println("Titulo: " + material.getTitulo());
            System.out.println("Autor: " + material.getAutor());
            System.out.println("Dias de prestamo: "
                    + material.diasPrestamoMaximo());

            System.out.println("--------------------------");
        }

        System.out.println();

        prestamo1.mostrarPrestamo();

        System.out.println();

        prestamo2.mostrarPrestamo();
    }
}
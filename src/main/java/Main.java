public class Main {
    public static void main(String[] args) throws Exception {
        while(true) {
            Bird bird=new Bird();
            bird.say();
            Thread.sleep(3000);
        }
    }
}

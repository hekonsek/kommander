package com.github.hekonsek.kommander;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        new SshServerBuilder().port(6666).build().start();
        Thread.sleep(1000 * 1000);
    }

}

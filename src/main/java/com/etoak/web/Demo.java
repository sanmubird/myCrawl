package com.etoak.web;

import com.blade.Blade;

public class Demo {

    public static void main(String[] args) {

        Blade blade = Blade.me();

        blade.listen(9090);

        blade.get("/", (req, res) -> {
            res.redirect("/index/index");
        });

        blade.start(Demo.class, "0.0.0.0" , 9090 );
    }
}

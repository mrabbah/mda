// Place your Spring DSL code here
beans = { 
    auteurWindow(AuteurWindow) { bean ->
        bean.scope = "prototype"
        bean.autowire = "byName"
    }
    livreWindow(LivreWindow) { bean ->
        bean.scope = "prototype"
        bean.autowire = "byName"
    }
    etudiantWindow(EtudiantWindow) { bean ->
        bean.scope = "prototype"
        bean.autowire = "byName"
    }
}
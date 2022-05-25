package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {


        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<T>(verdi, null);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q
        p.forelder=q;                            // q  blir forelder til p

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi) {

        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (b!=null){
                b.forelder=q;
            }
            if (p == rot){
                rot = b;
            }
            else if (p == q.venstre){

                q.venstre = b;

            }

            else{

                q.høyre = b;


            }
        }
        else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public int fjernAlle(T verdi) {

        int verdiAntall = 0; //antall fjerninger
        while (fjern(verdi)){
            verdiAntall++;      //legger til 1 for hver fjerning
        }
        return verdiAntall;
    }

    public int antall(T verdi) {

        int like = 0; //tellevariabel for antal like

        Node<T> p = rot;

            while (p != null) { //while løkke som samenligner variablene for å finne den vi ser etter
                int cmp = comp.compare(verdi, p.verdi);
                if (cmp < 0) p = p.venstre;
                else if (cmp > 0) p = p.høyre;
                else {  //når riktig variabel er funent
                    like++; //tellevariabel for like
                    p=p.høyre;  //like ligger alltid på høøyre side
                }
            }
      return like;
    }

    public void nullstill() {
            if (!tom()) nullstill(rot);  // nullstiller
            rot = null; antall = 0;      // treet er nå tomt
            endringer++;   // treet er endret
        }

        private void nullstill(Node<T> p)
        {
            if (p.venstre != null) {
                nullstill(p.venstre);      // venstre subtre
                p.venstre = null;          // nuller peker
            }

            if (p.høyre != null) {
                nullstill(p.høyre);        // høyre subtre
                p.høyre = null;            // nuller peker
            }

            p.verdi = null;              // nuller verdien
        }


    private static <T> Node<T> førstePostorden(Node<T> p) {
        while(true){
            if (p.venstre!=null){ //går alltid til venstre hvis den ikke er null
                p=p.venstre;
            }
            else if (p.høyre!=null){ //hvis venstre er null prøver den å gå til høyre
                p=p.høyre;
            }
            else return p; //bladnoden lengst til venstre
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        if (p.forelder==null){      //P er rot
            return null;
        }

        if (p.forelder.høyre==null || p.forelder.høyre==p){
            return p.forelder;
        }

        p=p.forelder.høyre;

        while (p.venstre!=null){
            p=p.venstre;
        }

        while (p.høyre!=null){
            p=p.høyre;
        }

        return p;

    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p= rot;

        p= førstePostorden(p);      //finner første postorden
        oppgave.utførOppgave(p.verdi);

        if(nestePostorden(p)==null){    //hvis det ikke finner mer en rot
            return;
        }

        while (nestePostorden(p)!=null){        //ittererer til neste postorden frem til den er gjenom hele treet
            p=nestePostorden(p);
            oppgave.utførOppgave(p.verdi);
        }

    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {


        if(p.venstre!=null){
            postordenRecursive(p.venstre, oppgave);
        }

        if (p.høyre!=null){
            postordenRecursive(p.høyre, oppgave);
        }
        oppgave.utførOppgave(p.verdi);




    }

    public ArrayList<T> serialize() {

        ArrayList<T> liste= new ArrayList<T>();

        if (tom()) return liste; // tomt tre

        ArrayDeque<Node <T>> ko = new ArrayDeque<Node <T>>();

        ko.add(rot);                     // legger inn roten

        while (!ko.isEmpty())                    // så lenge som køen ikke er tom
        {
            Node<T> p = ko.poll();             // tar ut fra køen
            liste.add(p.verdi);

            if (p.venstre != null) {
                ko.add(p.venstre);
            }
            if (p.høyre != null) {
                ko.add(p.høyre);
            }
        }

    return liste;
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tre = new SBinTre<K>(c);


        //ittererer gjennom hele listen og legger de til treet ved hjlp av legginn
        for (K verdi : data){
            tre.leggInn(verdi);
        }

        return tre;
    }


} // ObligSBinTre

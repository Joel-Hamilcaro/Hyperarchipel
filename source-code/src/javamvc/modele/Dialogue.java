/*
*
* Nom de la classe : Dialogue 
* 
* Description : 
*   Gestionnaire des dialogues du jeu (tutoriel, scénario ...)
*
* Version : 1.0
*
* Date : Mai 2019
*
* Auteur : PI4 / HASHIWOKAKERO1
*
*/
package javamvc.modele;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public abstract class Dialogue {

    // ATTRIBUTS *******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    private static ListIterator<String> dialogue;
    
    /**
     * Citation de victoire. "Kejuan Amaru : "Je n'ai fait que mon devoir."
     */
    public static final String VICTOIRE1
            = " Kejuan Amaru : \"Je n'ai fait que mon devoir.\"";

    /**
     * Citation de victoire. "Kejuan Amaru : "Les habitants sont sains et
     * saufs... Mais n'oublions pas qu'ils ont tout perdu."
     */
    public static final String VICTOIRE2
            = "Kejuan Amaru : \"Les habitants sont sains et saufs...\n"
            + "Mais n'oublions pas qu'ils ont tout perdu.\"";

    /**
     * Citation de victoire. "Kejuan Amaru : "Tant qu'il y aura des gens à
     * sauver, je ne prendrai aucun repos."
     */
    public static final String VICTOIRE3
            = "Kejuan Amaru : \"Tant qu'il y aura des gens à sauver,\n"
            + "je ne prendrai aucun repos.\"";

    /**
     * Citation de victoire. "Quito Lorenzo : "Mais qu'est-ce qu'... Ah, aucune
     * victime ? Vous voyez, la sécurité des citoyens c'est ma spécialité."
     */
    public static final String VICTOIRE4
            = "Quito Lorenzo : \"Mais qu'est-ce qu'... Ah, aucune victime ?\n"
            + "Vous voyez, la sécurité des citoyens c'est ma spécialité.\"";

    /**
     * Citation de victoire. "Quito Lorenzo : C'est vraiment une machine
     * incroyable !"
     */
    public static final String VICTOIRE5
            = "Quito Lorenzo : \"C'est vraiment une machine incroyable !\"";
    

    /**
     * Citation de défaite. "Kejuan Amaru : "Voir souffrir les siens est l'une
     * des pires souffrances que l'homme puisse connaître."
     */
    public static final String DEFAITE1
            = "Kejuan Amaru : \"Voir souffrir les siens est l'une des\n"
            + "pires souffrances que l'homme puisse connaître.\"";

    /**
     * Citation de défaite. "Kejuan Amaru : Peut-on encore dire que l'on vit
     * quand tout ce pourquoi on luttait n'est plus ?"
     */
    public static final String DEFAITE2
            = "Kejuan Amaru : \"Peut-on encore dire que l'on vit quand\n"
            + "tout ce pourquoi on luttait n'est plus ?\"";

    /**
     * Citation de défaite. "Quito Lorenzo : Vous voyez ce que cela donne de
     * vivre isolés comme des primates ?"
     */
    public static final String DEFAITE3
            = "Quito Lorenzo : \"Vous voyez ce que cela donne de vivre\n"
            + "isolés comme des primates ?\"";

    /**
     * Citation de défaite. "Quito Lorenzo : Une fois de plus, les indigènes se
     * sont montrés incapables d'écouter de simples consignes."
     */
    public static final String DEFAITE4
            = "Quito Lorenzo : \"Une fois de plus, les indigènes se sont\n"
            + "montrés incapables d'écouter de simples consignes.\"";

    /**
     * Citation de défaite. "Quito Lorenzo : "C'était bien la peine de leur
     * envoyer mon précieux BAIO.";
     */
    public static final String DEFAITE5
            = "Quito Lorenzo : \"C'était bien la peine de leur envoyer\n"
            + "mon précieux BAIO.\"";

    /**
     * Citation de défaite. "Quito Lorenzo : "Et qui est-ce qu'on va accuser
     * maintenant ?"
     */
    public static final String DEFAITE6
            = "Quito Lorenzo : \"Et qui est-ce qu'on va accuser\n"
            + "maintenant ?\"";

    // ACCESSEURS ******************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * ACCESSEUR 
     * @return liste des texte du dialogue.
     * Chaque texte correspond à une boite de dialogue et s'affiche un par un.
     * Attention : 
     * Pour le dialogue initialisé par la fonction dialogueLettreParLettre, 
     * chaque texte de la liste diffère du suivant d'un seul caractère. 
     */
    public static ListIterator<String> getDialogue() {
        return dialogue;
    }
    
    // METHODES ********************************************************************
    //******************************************************************************
    //******************************************************************************
    //******************************************************************************
    
    /**
     * Choisi une citation de victoire aléatoire.
     *
     * @return Une citation de victoire.
     */
    public static String randomFin() {
        String[] t = new String[5];
        t[0] = Dialogue.VICTOIRE1;
        t[1] = Dialogue.VICTOIRE2;
        t[2] = Dialogue.VICTOIRE3;
        t[3] = Dialogue.VICTOIRE4;
        t[4] = Dialogue.VICTOIRE5;
        return t[new Random().nextInt(5)];
    }

    /**
     * Choisi une citation de défaite aléatoire.
     *
     * @return Une citation de défaite.
     */
    public static String randomDef() {
        String[] t = new String[6];
        t[0] = Dialogue.DEFAITE1;
        t[1] = Dialogue.DEFAITE2;
        t[2] = Dialogue.DEFAITE3;
        t[3] = Dialogue.DEFAITE4;
        t[4] = Dialogue.DEFAITE5;
        t[5] = Dialogue.DEFAITE6;
        return t[new Random().nextInt(5)];
    }

    /**
     * Converti un texte en liste de texte lettre par lettre. (utile à
     * l'animation de l'écran titre).
     *
     * @param s Texte
     */
    public static void dialogueLettreParLettre(String s) {
        dialogue = new ArrayList<String>().listIterator();
        String s2 = "";
        for (int i = 0; i < s.length(); i++) {
            s2 += s.charAt(i);
            dialogue.add(s2);
        }
        while (Dialogue.getDialogue().hasPrevious()) {
            Dialogue.getDialogue().previous();
        }
    }

    /**
     * Initialise les dialogues scriptés.
     *
     * @param codeEtape Choix du dialogue 
     * 1 à 4 :  Chapitre 1 à 4
     * 5 : Fin de chapitres.
     * 6 : Crédits
     * 7 à 15 : Etape du tuto.
     * @param varianteEtape
     * Dépend du codeEtape :
     * 1 à 4 : 
     *  true : Narration
     *  false : Dialogue personnages.
     * 5 : 
     *  true : Victoire
     *  false : Défaite
     * 6 à 15 : 
     *  aucune importance.
     */
    public static void initDialogue(int codeEtape, boolean varianteEtape) {

        switch (codeEtape) {
            case 1:
                dialogue = new ArrayList<String>().listIterator();
                if (varianteEtape) {
                    dialogue.add(""
                            + "CHAPITRE 1 \n\n(Cliquez sur l'écran pour continuer)"
                    //+"\neiusmod tempor incididunt ut labore et dolore magna aliqua. "
                    );
                    dialogue.add(""
                            + "Hyperarchipel du Pineda - Zone C \n"
                            + "9 février 2431"
                    );
                    dialogue.add(""
                            + "Niveau de la mer : ..."
                    );
                    dialogue.add(""
                            + "2,8 mètres au dessus de la moyenne."
                    );
                    dialogue.add(""
                            + "Niveau d'alerte fixé par les autorités : ..."
                    );
                    dialogue.add(""
                            + "Nécessite un contrôle de routine."
                    );
                    dialogue.add(""
                            + "État de la population locale : ... "
                    );
                    dialogue.add(""
                            + "Surprise."
                    );
                    dialogue.add(""
                            + "Le gouverneur de l'hyperarchipel, Quito Lorenzo,\n"
                            + "s'est toujours fort peu préoccupé des indigènes qui\n"
                            + "manifestaient pour défendre la forêt et empêcher\n"
                            + "l'exploitation sauvage de leurs ressources.\n"
                    );
                    dialogue.add(""
                            + "Fidèle à sa réputation, il ne dépêcha qu'un seul\n"
                            + "de ses modèles d'engin de combat. Il envoya le \n"
                            + "BAIO-GNG faire un contrôle dans la Zone C,\n"
                            + "par peur des sanctions des organismes de régulation\n"
                            + "internationaux.\n"
                    );
                    dialogue.add(""
                            + "Une fois sur place, le pilote Amaru pouvait constater\n"
                            + "toute l'étendue des dégâts. Environ 40% des habitations\n"
                            + "côtières de la Zone C sont déjà en ruines et l'eau\n"
                            + "semble monter à vue d'œil.\n"
                    );
                    dialogue.add(""
                            + "Bravant les consignes de ses supérieurs, il va\n"
                            + "prendre la plus grande initiative de toute sa \n"
                            + "vie..."
                    );

                } else {
                    dialogue.add(""
                            + "\"CECI EST UN MESSAGE DESTINÉ À TOUS LES RÉSIDENTS\n"
                            + "DE LA ZONE C !\"\n"
                    );
                    dialogue.add(""
                            + "\"C'EST LE PILOTE DU BAIO QUI VOUS PARLE !\"\n"
                    );
                    dialogue.add(""
                            + "\"ABANDONNEZ TOUT CE QUE VOUS ÊTES EN TRAIN DE FAIRE\n"
                            + "ET ÉVACUEZ VOTRE DOMICILE !\"\n"
                    );
                    dialogue.add(""
                            + "\"JE VAIS ETABLIR UNE STRUCTURE DE SAUVETAGE SUR LE\n"
                            + "BAIOPORT NORD-OUEST DE L'ILE FEJDIO.\"\n"
                    );
                    dialogue.add(""
                            + "\"DÉTRUISEZ CE QU'IL RESTE DE VOS MAISONS ET FAITES\n"
                            + "EN DES PONTS DE FORTUNE POUR REJOINDRE CETTE ILE.\""
                    );
                    dialogue.add(""
                            + "\"CECI N'EST PAS UN EXERCICE. JE RÉPÈTE : CECI N'EST\n"
                            + "PAS UN EXERCICE. C'EST VOTRE SEUL ESPOIR !\""
                    );
                }
                break;

            case 2:
                dialogue = new ArrayList<String>().listIterator();
                if (varianteEtape) {
                    dialogue.add(""
                            + "CHAPITRE 2 \n\n(Cliquez sur l'écran pour continuer)"
                    );
                    dialogue.add(""
                            + "Hyperarchipel du Pineda - Zone A"
                            + "\n Bloc 3 : Compartiment 23"
                            + "\n Logement des Amaru "
                            + "\n 20 juillet 2417"
                    );
                    dialogue.add(""
                            + "Nia Amaru : "
                            + "\n\n\"Sèche tes larmes mon fils, maintenant "
                            + "\nc'est toi l'homme de la maison. Mon souhait c'est que"
                            + "\ntu vives comme il l'a toujours fait, avec dignité, "
                            + "\nintelligence et respect. Ce sont les gens comme toi qui "
                            + "\nfiniront par leur montrer que nous vallons tout autant "
                            + "\nqu'eux et que le savoir ne leur est pas réservé.\""
                    );
                    dialogue.add("" // trigger index 3
                            + "Goravio - Capitale continentale du Pineda"
                            + "\n Palais Quito Lorenzo"
                            + "\n 15 février 2431 "
                    ); 
                    dialogue.add(""
                            + "Quito Lorenzo : "
                            + "\n\n\"Pour sa bravoure exceptionnelle et pour "
                            + "\nl'exemple d'intégration qu'il constitue, la Fédération "
                            + "\ndes Terres du Pineda remet à Kejuan Amaru le titre de ...\""  
                    );
                    dialogue.add(""
                            + "Assistant :"
                            + "\n\n\"Chef Suprême, Chef Suprême, c'est "
                            + "\nla même chose qui se répète dans la zone B !\""         
                    );
                    dialogue.add(""
                            + "Quito Lorenzo (à Kejuan) : "
                            + "\n\n\"Vas-y maintenant Soldat Amaru ... \" "             
                    );
                    dialogue.add("" // trigger index 7
                           +"\"et n'échoue pas !\" "             
                    );
                    dialogue.add("" // trigger index 8
                            + "Hyperarchipel du Pineda - Zone B"
                            + "\n 15 février 2431 "
                    );
                    dialogue.add(""
                            + "Niveau de la mer : ..." 
                    );
                    dialogue.add(""
                            + "Non déterminé à temps."
                    );
                    dialogue.add(""
                            + "Niveau d'alerte fixé par les autorités : ..."
                    );
                    dialogue.add(""
                            + "Non déterminé à temps."
                    );
                    dialogue.add(""
                            + "État de la population locale : ... "
                    );
                    dialogue.add(""
                            + "Médusée."
                    );
                    break;
                    
                } else {
                    dialogue.add(""
                            + "\"HABITANTS DE LA ZONE B, VOUS CONNAISSEZ LA PROCÉDURE.\""
                    );
                    dialogue.add(""
                            + "\"IL NOUS RESTE PEU DE TEMPS. "
                            + "\nCONVERGEZ VERS LA STRUCTURE DE SAUVETAGE !\""  
                    );
                    break;
                }
            case 3:
                dialogue = new ArrayList<String>().listIterator();
                if (varianteEtape) {
                    dialogue.add(""
                            + "CHAPITRE 3 \n\n(Cliquez sur l'écran pour continuer)"
                    );
                    dialogue.add(""
                            + "Banlieue de Goravio"
                            + "\nLaboratoire Sebi"
                            + "\n5 novembre 2418, 21h36"
                    );
                    dialogue.add(""
                            + "Philipp Banks, le père de Lisa travaillait depuis de "
                            + "\nlongs mois sur une série de mesures anormales partant "
                            + "\nde la Zone Confidentielle AA de l'hyperarchipel."
                    );
                    dialogue.add(""
                            + "Ce lieu fortement éloigné des autres zones, connu "
                            + "\nuniquement par les élites gouvernementales, "
                            + "\nscientifiques et les plus importants investisseurs "
                            + "\nde la Fédération, était pourtant l'une des zones les "
                            + "\nplus vastes de l'hyperarchipel, avec une réserve "
                            + "\nde painite sans pareil dans le monde."
                    );
                    dialogue.add(""
                            + "Le gouvernement et la société P&P (Painite & Products), "
                            + "\nayant un monopole sur l'extraction de ce minéral, "
                            + "\ntravaillaient conjointement pour cacher son existence"
                            + "\nà la population, en restreignant les libertés des "
                            + "\ncitoyens, leur demandant sans cesse plus d'efforts,"
                            + "\nsous prétexte que la Fédération manquait de ressources."
                    );
                    dialogue.add(""
                            + "Ce soir là, les recherches de M.Banks le confirment enfin, "
                            + "\ndes catastrophes sans précédent se préparent dans "
                            + "\nla décennie à venir, et surviendront lorsque les "
                            + "\ngisements de painite de la Zone AA seront vides, "
                            + "\nprovoquant ainsi une instabilité complète des sols."
                    );
                    dialogue.add(""
                            + "N'écoutant que sa morale, M.Banks lance une conférence"
                            + "\nholographique avec les autorités compétentes pour "
                            + "\nles alerter. Ni lui ni son interlocuteur ne s'aperçoivent "
                            + "\nde la présence discrète de Nia Amaru, technicienne de "
                            + "\nsurface qui a saisi l'importance de la conversation à venir."
                    );
                    dialogue.add(""
                            + "Au moment où M.Banks aborde le sujet, l'hologramme se"
                            + "\nbrouille et une nouvelle voix se fait entendre : "
                    ); 
                    dialogue.add(""
                            + "\"M.Banks, nous ne doutons pas de votre détermination "
                            + "\net de la solidité de vos convictions. Cependant,"
                            + "\nnous contestons vos résultats, et si l'idée vous vient"
                            + "\nune nouvelle fois de partager des informations aussi "
                            + "\nsensibles avec quelqu'un d'extérieur à la haute hiérarchie"
                            + "\nde P&P, faites en sorte que cela ne reste qu'une idée, où "
                            + "\nalors, ce sera la dernière chose que vous ferez ... "
                            + "\ndans votre vie.\""
                    ); 
                    dialogue.add(""
                            + "La conversation se coupe et Nia ne peut s'empêcher de lâcher "
                            + "\nses outils par stupeur. M.Banks se retourne immédiatement et "
                            + "\ncède à la panique. Ignorant que Nia avait, depuis le début, "
                            + "\nactivé son système de Retransmission Oculaire à  Distance, "
                            + "\n -indétectable, et quasiment inexistant chez les indigènes-"
                            + "\nil lui bondit dessus, et l'étrangle, avant de déposer son "
                            + "\ncorps dans le Broyeur de Matière Organique du laboratoire."
                    );
                    dialogue.add(""
                            + "Craignant pour sa vie, le chercheur pensait que cet acte était"
                            + "\nun moindre mal face à la possibilité que Nia parle de ce qu'elle"
                            + "\na vu, et que l'information remonte jusqu'à P&P. Les autorités, ne "
                            + "\ns'intéressant que peu aux affaires impliquant des indigènes et les "
                            + "\nfamilles n'ayant aucun moyen de pression, éliminer le témoin"
                            + "\ngênant, était pour lui, le meilleur choix possible."
                    );
                    dialogue.add("" // trigger index 10
                            + "Presque le meilleur ..."
                    ); 
                    dialogue.add("" // trigger index 11
                            + "Hyperarchipel du Pineda - Zone A"
                            + "\n 16 février 2431"
                    );
                    dialogue.add(""
                            + "Niveau de la mer : ..." 
                    );
                    dialogue.add(""
                            + "17,2 mètres au dessus de la moyenne."
                    );
                    dialogue.add(""
                            + "Niveau d'alerte fixé par les autorités : ..."
                    );
                    dialogue.add(""
                            + "Très préoccupant."
                    );
                    dialogue.add(""
                            + "État de la population locale : ... "
                    );
                    dialogue.add(""
                            + "Hystérie généralisée."
                    );
                    break;
                } else {
                    dialogue.add(""
                            + "\"HABITANTS DE LA ZONE A, J'AURAIS PRÉFÉRÉ REVENIR"
                            + "\nICI DANS DE MEILLEURES CONDITIONS MAIS VOUS DEVEZ "
                            + "\nSAVOIR QUE JE NE VOUS ABANDONNERAI JAMAIS ET QUE "
                            + "\nCE N'EST PAS AUJOURD'HUI QUE JE VAIS COMMENCER.\""
                    );
                    dialogue.add(""
                            + "\"J'AI TOUT AUSSI MAL QUE VOUS EN VOYANT NOS MAISONS "
                            + "\nET NOS TERRES SE FAIRE ENGLOUTIR PAR L'OCÉAN MAIS "
                            + "\nVOUS DEVEZ VIVRE. JE VOUS SUPPLIE DE M'ÉCOUTER.\""
                    );
                    break;
                }
            case 4:
                dialogue = new ArrayList<String>().listIterator();
                if (varianteEtape) {
                    dialogue.add(""
                            + "CHAPITRE FINAL \n\n(Cliquez sur l'écran pour continuer)"
                    );
                    dialogue.add(""
                            + "Banlieue de Goravio"
                            + "\nLaboratoire Sebi"
                            + "\nSortie des employés"
                            + "\n6 novembre 2418"
                    );
                    dialogue.add(""
                            + "De l'autre côté de la R.O.D. se trouvait Kejuan, "
                            + "\nfils de Nia et unique élément non prévu dans le "
                            + "\nplan de M.Banks."
                    );
                    dialogue.add(""
                            + "Aguerri par 10 ans de vie dans la Zone A, l'enfant "
                            + "\nattend le chercheur avec une sarbacane dans laquelle "
                            + "\nest logée une flèche trempée dans une Phyllobates "
                            + "\nterribilis. Dès qu'il reconnaît le visage qu'il a vu"
                            + "\ndans la retransmission, il souffle, et atteint sa"
                            + "\ncible en plein dans la tempe."
                    );
                    dialogue.add(""
                            + "Encore trop faible pour soulever un cadavre adulte,"
                            + "\nil le laisse sur place et s'échappe furtivement."
                    );
                    dialogue.add(""
                            + "Repéré entrain d'errer dans la zone continentale, "
                            + "\nil est officiellement \"recueilli\" par une "
                            + "\ncamionette des forces armées."
                    );
                    dialogue.add(""
                            + "Il sera formé à toutes les techniques, se spécialisera "
                            + "\ndans le pilotage des engins aériens, mais apprendra"
                            + "\nsurtout que le plus important dans cette Fédération"
                            + "\nest d'être perçu comme fidèle au régime."
                    );
                    dialogue.add(""
                            + "Il faudra qu'il attende le jour de ses 18 ans pour "
                            + "\npouvoir accéder de nouveau aux documents que lui "
                            + "\navait transmis sa mère ce jour-là sur son LifeAccount, "
                            + "\net pouvoir les partager à nouveau."
                    ); 
                    dialogue.add("" // trigger index 8
                            + "Il les enverra de manière anonyme à Lisa, ce qui "
                            + "\npermettra à cette dernière de reprendre les recherches "
                            + "\nson père, et de nommer la région à risque en 2428 en "
                            + "\névitant de faire ressurgir l'histoire de la zone AA. "
                    ); 
                    dialogue.add(""
                            + "Goravio - Capitale continentale du Pineda"
                            + "\nBureaux de P&P"
                            + "\n6 novembre 2418"
                    );
                    dialogue.add(""
                            + "Secrétaire de l'assemblée : "
                            + "\n\n\"Les 12 membres ici présents ont voté à l'unanimité "
                            + "\npour la démolition planifiée et simultanée des gisements"
                            + "\nde painite dans la Zone Confidentielle AA, suite aux "
                            + "\nrécents événements et afin d'éviter des fouilles "
                            + "\npréventives qui pourraient compromettre l'entreprise.\""
                    );
                    dialogue.add("" // trigger index 11
                            + "Goravio - Capitale continentale du Pineda"
                            + "\nPalais Quito Lorenzo"
                            + "\nBureau du Gouverneur"
                            + "\n25 février 2431, 12h20"
                    );
                    dialogue.add(""
                            + "Quito Lorenzo : "
                            + "\n\n\"Soldat Amaru, vous allez dans quelques minutes monter"
                            + "\ndans votre BAIO, cette fois-ci pour sauver une terre dont "
                            + "\nvous ne connaissez pas l'existence et qui ne connaît pas "
                            + "\nnon plus l'existence des autres zones.\""
                    );
                    dialogue.add("" 
                            + "Quito Lorenzo : "
                            + "\n\n\"Il s'agit de la Zone AA, qui sera engloutie à 13h00 si personne "
                            + "\nne fait rien. Vous avez démontré une loyauté sans faille "
                            + "\nau régime et nous vous récompensons en vous confiant cette "
                            + "\nmission confidentielle connue par moins de 50 personnes et "
                            + "\nd'une importance capitale. Vous sentez-vous à la hauteur ?\""
                    ); 
                    dialogue.add(""
                            + "Kejuan Amaru : "
                            + "\n\n\"Oui Chef Suprême\""
                    );
                    dialogue.add("" // trigger index 15
                            + "Hyperarchipel du Pineda - Zone Confidentielle AA"
                            + "\n 16 février 2431, 12h58"
                    );
                    dialogue.add(""
                            + "Niveau de la mer : ..." 
                    );
                    dialogue.add(""
                            + "Rien à signaler"
                    );
                    dialogue.add(""
                            + "Niveau d'alerte fixé par les autorités : ..."
                    );
                    dialogue.add(""
                            + "Rien à signaler"
                    );
                    dialogue.add(""
                            + "État de la population locale : ... "
                    );
                    dialogue.add(""
                            + "Rien à signaler"
                    );
                    dialogue.add(""
                            + "Kejuan Amaru : "
                            + "\n\n\"Comment peut-il connaître l'heure de la catastrophe ? "
                            + "\nEt dans ce cas pourquoi me prévenir si tard ?\""
                    );
                    dialogue.add(""
                            + "Kejuan Amaru : "
                            + "\n\n\"Si ces gens vivaient dans leur bulle jusqu'à maintenant, "
                            + "\ncela semble impossible qu'il brise le secret aujourd'hui.\""
                    );
                    dialogue.add(""
                            + "Kejuan Amaru : "
                            + "\n\n\"Ou bien va-t-il en finir avec nous une fois regroupés"
                            + "\ndans le BAIO ?\""
                    );
                    dialogue.add(""
                            + "Kejuan Amaru : "
                            + "\n\n\"Non, je dois être fatigué pour imaginer de telles choses. "
                            + "\nMême après tout ce que j'ai entendu sur lui ...\""
                    );
                    dialogue.add("" // trigger index 11
                            + "Goravio - Capitale continentale du Pineda"
                            + "\nPalais Quito Lorenzo"
                            + "\nBureau du Gouverneur"
                            + "\n25 février 2431, 12h59"
                    );
                    dialogue.add("" // trigger index 26
                            + "Quito Lorenzo : "
                            + "\n\n\"Nous n'en aurons jamais d'autre comme lui ...\""
                    );
                    dialogue.add("" // trigger index 26
                            + "..."
                    );
                    break;
                } else {
                    dialogue.add(""
                            + "\"HABITANTS DE LA ZONE AA, JE SAIS QUE VOUS AVEZ "
                            + "\nBEAUCOUP DE QUESTIONS MAIS NOUS N'AVONS PAS LE "
                            + "\nTEMPS DE DISCUTER.\""
                    );
                    dialogue.add(""
                            + "\"L'HEURE EST EXTRÊMEMENT GRAVE. DANS MOINS D'UNE "
                            + "\nMINUTE LES SOLS VONT IMPLOSER ET SOMBRER DANS L'EAU."
                            + "\nJE M'APPELLE KEJUAN AMARU ET JE SUIS ICI POUR "
                            + "\nVOUS SAUVER\""
                    );
                    dialogue.add(""
                            + "\"JE NE CONNAIS PAS CETTE ZONE. JE VAIS VOLER AUTOUR "
                            + "\nDE L'ÎLE LA PLUS ADAPTÉE POUR Y ÉTABLIR UNE STRUCTURE "
                            + "\nDE SAUVETAGE. QUAND VOUS LA VERREZ, CONVERGEZ VERS ELLE !\""
                    );
                    break;
                }

            case 5: // Fin
                dialogue = new ArrayList<String>().listIterator();
                if (varianteEtape) {
                    dialogue.add(""
                            + randomFin()
                    );
                    break;
                } else {
                    dialogue.add(""
                            + randomDef()
                    );
                    break;
                }

            case 6: // Credits
                dialogue = new ArrayList<String>().listIterator();
                
                dialogue.add("HYPERARCHIPEL");
                dialogue.add("Une réalisation EQU1PE");
                
                dialogue.add("Projet informatique");
                dialogue.add("Université de Paris \n Université Paris 7 Diderot");
                
                dialogue.add("Equipe développeurs");
                dialogue.add("@PI4HashiWokakero1");
                
                dialogue.add("Ninoh Agostinho Da Silva");
                dialogue.add("MIASHS \n Parcours Linguistique");
                
                dialogue.add("Joël Hamilcaro");
                dialogue.add("Double Licence \n Mathématiques-Informatique");
                
                dialogue.add("Jie Tu");
                dialogue.add("Double Licence \n Mathématiques-Informatique");
                
                dialogue.add("José Ralph");
                dialogue.add("Double Licence \n Mathématiques-Informatique");

                dialogue.add("Représentant du groupe");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Cahier des charges");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Modélisation UML");
                dialogue.add("José Ralph");
                
                dialogue.add("Rédacteur javadoc");
                dialogue.add("Jie Tu");
                    
                dialogue.add("Mise en commun du code");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Git-pusher");
                dialogue.add("Ninoh Agostinho Da Silva");      
                
                dialogue.add("Responsable modèle MVC");
                dialogue.add("Jie Tu");
                
                dialogue.add("Modélisation des plateaux");
                dialogue.add("José Ralph"); 
                
                dialogue.add("Programmation des plateaux");
                dialogue.add("Jie Tu");                 
                
                dialogue.add("Programmation des actions de jeu");
                dialogue.add("Jie Tu");                              
                
                dialogue.add("Programmation des jokers");
                dialogue.add("Joël Hamilcaro");   
                
                dialogue.add("Programmation du chronomètre");
                dialogue.add("Jie Tu");    
                
                dialogue.add("Programmation du système de sauvegarde");
                dialogue.add("Joël Hamilcaro");   
                
                dialogue.add("Programmation du traducteur matrice-niveaux");
                dialogue.add("Joël Hamilcaro");  
                
                dialogue.add("Création de la liste des niveaux");
                dialogue.add("José Ralph");
                
                dialogue.add("Programmation du générateur de plateaux");
                dialogue.add("Jie Tu");
                
                dialogue.add("Algorithmique de backtracking");
                dialogue.add("José Ralph");
                
                dialogue.add("Programmation du solveur");
                dialogue.add("José Ralph");
                
                dialogue.add("Optimisation du solveur");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Programmation du vérificateur d'unicité");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Optimisation du vérificateur d'unicité");
                dialogue.add("Jie Tu");
                
                dialogue.add("Programmation du système de score");
                dialogue.add("Jie Tu");
                
                dialogue.add("Corrections du modèle");
                dialogue.add("José Ralph");
                
                dialogue.add("Responsable de l'interface graphique");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Interface graphique des plateaux");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Choix des variantes de gameplay");
                dialogue.add("José Ralph");
                
                dialogue.add("Programmation du gameplay");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Synchronisation vues-modèle MVC");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Programmation du menu principal");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Programmation du menu pause");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Programmation du menu de fin");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Programmation de l'écran titre");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Implémentation des dialogues");
                dialogue.add("José Ralph");
                
                dialogue.add("Script du tutoriel");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Programmation du tutoriel");
                dialogue.add("Joël Hamilcaro");
                
                dialogue.add("Script du scénario");
                dialogue.add("Ninoh Agostinho Da Silva");
               
                dialogue.add("Animation du BAIO");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Animation de la population");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Chronomètrage du scénario");
                dialogue.add("Jie Tu");
                
                dialogue.add("Design du chapitre final");
                dialogue.add("Jie Tu");            
                
                dialogue.add("Design de l'écran de chargement");
                dialogue.add("José Ralph");
                
                dialogue.add("Animation des crédits");
                dialogue.add("Ninoh Agostinho Da Silva");              

                dialogue.add("Gestion des icones");
                dialogue.add("Jie Tu");
                
                dialogue.add("Gestion des ressources d'images");
                dialogue.add("José Ralph");

                dialogue.add("Gestion des sons et musiques");
                dialogue.add("José Ralph");
                
                dialogue.add("Tests finaux");
                dialogue.add("José Ralph");
                
                dialogue.add("Débogage final");
                dialogue.add("Jie TU");
                
                dialogue.add("Rapport de projet");
                dialogue.add("Ninoh Agostinho Da Silva");
                
                dialogue.add("Sources des icônes des menus");
                dialogue.add("icones8.fr");
                
                dialogue.add("Musique de l'écran titre");
                dialogue.add("HolFix - Hollywood Trailer \n (Musique libre de droits)");
                
                dialogue.add("Musique du menu principal");
                dialogue.add("AShamaluevMusic - Documentary Thriller \n (Musique libre de droits)");
                
                dialogue.add("Effet sonore des niveaux");
                dialogue.add("Crisp Ocean Waves - Mike Koenig\n(Creative Commons BY 3.0)");
                
                dialogue.add("Musique des premiers chapitres");
                dialogue.add("Fire And Thunder - Cjbeards\n(Proposée par \"La Musique Libre\")");
                
                dialogue.add("Musique du chapitre final");
                dialogue.add("Are You With Us? - Wontolla\n(Musique libre composée par Wontolla)");
                
                dialogue.add("Musique des crédits");
                dialogue.add("Fire And Thunder - Cjbeards\n(Musique libre composée par Cjbeards)");
                
                dialogue.add("D'après une adaption du jeu de logique");
                dialogue.add("橋をかけろ / HASHIWOKAKERO");   
                
                dialogue.add(" ");
                dialogue.add(" ");   
                
                
                break;

            case 7: // Tuto
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Bienvenue ! \n\n(Cliquez sur l'écran pour continuer)"
                        + "\n\n(Vous pouvez quitter le tutoriel à tout moment"
                        + "\nen appuyant sur la touche ECHAP)"); //1
                dialogue.add("HYPERARCHIPEL reprend le principe du jeu de logique \n"
                        + "橋をかけろ (Hashiwokakero).");//2
                dialogue.add("Votre but sera de construire des ponts entre les\n"
                        + "iles ci-dessus.");//3
                dialogue.add("Ces ponts doivent former des chemins permettant\n"
                        + "de relier toutes les iles entre elles."); //4
                dialogue.add("Cependant ... "); //5
                dialogue.add("Plusieurs contraintes vous seront imposées."); //6
                dialogue.add("Tout pont débute et finit sur une île."); //7
                dialogue.add("Aucun pont ne peut en croiser un autre."); //8
                dialogue.add("Tous les ponts sont verticaux ou horizontaux."); //9
                dialogue.add("Le nombre de ponts qui passent sur une île doit\n"
                        + "correspondre au numero indiqué sur l'île."); //10
                dialogue.add("Il est possible de construire un pont simple\n"
                        + "ou un pont double entre deux iles."); //11
                dialogue.add("La solution de chaque plateau est unique."); //12
                dialogue.add("Prêt ? C'est parti ! Cliquez sur l'ile \n"
                        + "qui sera indiquée."); //13

                break;

            case 8: // Tuto
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Cette ile a été sélectionnée !"); //14
                dialogue.add("Cliquez sur la deuxième ile indiquée."); //15

                break;
            case 9: // Tuto
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Vous avez construit un pont !"); //16
                dialogue.add("Refaîtes de même pour construire un double pont."); //17

                break;

            case 10:
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Les croix signifient qu'il n'est plus possible de\n"
                        + "construire de pont entre ces iles."); //19
                dialogue.add("En refaisant la meme chose, vous allez détruire \n"
                        + "ces ponts. Faîtes-le !"); //20
                break;

            case 11:
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Il est possible d'activer le survol dans les\n"
                        + "paramètres. Dans ce cas, le deuxième clic n'est\n"
                        + "n'est plus nécessaire."); //22
                dialogue.add("Construisez un pont en cliquant sur l'île\n"
                        + "indiquée. Et survolez la seconde île avec la souris, sans la\n"
                        + "cliquer !"); //23     
                break;

            case 12:
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Sachez qu'il est aussi possible de detruire un \n"
                        + "pont en faisant un clic droit dessus."); //25
                dialogue.add("Tout comme il est possible de doubler un pont en\n"
                        + "faisant un clic gauche sur celui-ci."); //26
                dialogue.add("Vous pourrez découvrir ces subtilités plus tard par\n"
                        + "vous-même."); //27
                dialogue.add("HYPERARCHIPEL vous propose d'utiliser des jokers.\n"
                        + "Chaque joker n'est utilisable qu'une seule fois."); //28
                dialogue.add(""
                        + "Le Joker n°1 permet de construire un pont qui est\n"
                        + "conforme à la solution."); //29
                dialogue.add("Cliquez sur le joker n°1."); //30

                break;

            case 13:
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Comme vous le voyez, un pont a été construit."); //31
                dialogue.add("Cependant, une pénalité de cinq minutes a été\n"
                        + "rajoutée sur le chronomètre."); //32
                dialogue.add("Les pénalités se cummulent pour chaque joker utilisé."); //33
                dialogue.add("Réfléchissez bien avant d'utiliser un joker !"); //34
                dialogue.add("Passons au Joker suivant."); //35
                dialogue.add(""
                        + "Le Joker n°2 permet de détruire un pont qui n'est\n"
                        + "pas conforme à la solution."); //36
                dialogue.add("Testez-le !"); //37

                break;
            case 14:
                dialogue = new ArrayList<String>().listIterator();
                dialogue.add("Le pont qui a été détruit était donc incorrect."); //38
                dialogue.add("Attention, si tous les ponts du plateau étaient\n"
                        + "valides, le joker aurait été utilisé pour rien !"); //39
                dialogue.add("Utilisez ce joker avec précaution !"); //40
                dialogue.add("Enfin ..."); //41
                dialogue.add(""
                        + "Le Joker n°3 vous indique si la configuration d'une\n"
                        + "île est correcte, et ce, jusqu'à la fin de la partie.");//42
                dialogue.add(""
                        + "Tant que l'île reste en rouge, sa configuration n'est\n"
                        + "pas conforme à la solution."); //43
                dialogue.add("Cliquez sur le Joker n°3 !"); //44
                break;
            case 15:
                dialogue = new ArrayList<String>().listIterator();

                dialogue.add("Vous êtes maintenant prêt ! Essayez de résoudre\n"
                        + "ce plateaux par vous-même.");//45
                dialogue.add("Et n'oubliez pas qu'il est possible d'activer le\n"
                        + "survol à la souris dans les paramètres !"); //46
                dialogue.add("Nous vous laissons découvrir le reste par vous-même;"); //47
                dialogue.add("Bon courage !"); //48
                break;
            case 16 :
                dialogue = new ArrayList<String>().listIterator();

                dialogue.add("A SUIVRE ...");
        }
        while (Dialogue.getDialogue().hasPrevious()) {
            Dialogue.getDialogue().previous();
        }
    }

    public static String titre() {
        return ""
                + "                                                                          \n"
                + "HYPERARCHIPEL\n[ipɛʁaʁʃipɛl] n.m.\n\n\n"
                + "                                                                          \n"
                + "Désigne un ensemble d'archipels, naturels ou artificiels,\n "
                + "couvrant chacun une surface égale ou supérieure à \n"
                + "50000 km².\n"
                + "                                                                          \n"
                + "Dès la première moitié du XXVème siècle, la géologiste \n"
                + "Lisa Banks étudiait les nouveaux phénomènes climatiques\n"
                + "d'ampleur inégalée qui touchaient la région du Pineda.\n"
                + "                                                                          \n"
                + "Le secteur continental était épargné. \n"
                + "                                                                          \n"
                + "Néanmoins...\n\n"
                //+"                                                                          \n"
                + "La plus grosse partie de la région était affectée.\n"
                + "                                                                          \n"
                + "En 2428, Banks nomma cette zone en utilisant pour\n"
                + "la première fois le terme :\n"
                + "                                                                           ";
    }
}

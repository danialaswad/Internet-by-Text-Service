package web;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import web.PageCutter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PageCutterTest {

    @Test
    public void cutPageTest(){
        String html ="<body> \n" +
                "  <div>\n" +
                "    Hello \n" +
                "   <i> new </i> \n" +
                "   <p class=\"unecessary\" id=\"necessary\">this must <i>not</i> be remove</p> world \n" +
                "  </div> \n" +
                "  <p id=\"n\">this must not be remove</p> \n" +
                " </body>\n";
        PageCutter p = new PageCutter(html);
        String trimedPage = p.subHtmlTags(0,24);
        Assert.assertEquals("<body> \n" +
                "  <div>\n" +
                "    Hell",html.substring(0,24));

        String body = "<body> \n"+ " ";
        Assert.assertEquals(body, trimedPage);



        Assert.assertEquals("  <div>\n" +
                "    Hello \n" +
                "   <i> new </i> \n" +
                "   <p class=\"unecessary\" id=\"ne",html.substring(8,75) );
        trimedPage = p.subHtmlTags(8,75);
        String res = " <div>\n" +
                "   Hello \n" +
                "  <i> new </i> \n" + "  ";
        Assert.assertEquals(res, trimedPage);
    }


    @Test
    public void getFirstChunkTest(){
        String html ="<body class=\"mediawiki ltr sitedir-ltr mw-hide-empty-elt ns-0 ns-subject page-Vampire rootpage-Vampire skin-vector action-view\">\t\t<div id=\"mw-page-base\" class=\"noprint\"></div>\n" +
                "\t\t<div id=\"mw-head-base\" class=\"noprint\"></div>\n" +
                "\t\t<div id=\"content\" class=\"mw-body\" role=\"main\">\n" +
                "\t\t\t<a id=\"top\"></a>\n" +
                "\n" +
                "\t\t\t\t\t\t\t<div id=\"siteNotice\" class=\"mw-body-content\"><!-- CentralNotice --></div>\n" +
                "\t\t\t\t\t\t<div class=\"mw-indicators mw-body-content\">\n" +
                "</div>\n" +
                "\t\t\t<h1 id=\"firstHeading\" class=\"firstHeading\" lang=\"fr\">Vampire</h1>\n" +
                "\t\t\t\t\t\t\t\t\t<div id=\"bodyContent\" class=\"mw-body-content\">\n" +
                "\t\t\t\t\t\t\t\t\t<div id=\"siteSub\">Un article de Wikipédia, l&#039;encyclopédie libre.</div>\n" +
                "\t\t\t\t\t\t\t\t<div id=\"contentSub\"></div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"jump-to-nav\" class=\"mw-jump\">\n" +
                "\t\t\t\t\tAller à :\t\t\t\t\t<a href=\"#mw-head\">navigation</a>, \t\t\t\t\t<a href=\"#p-search\">rechercher</a>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div id=\"mw-content-text\" lang=\"fr\" dir=\"ltr\" class=\"mw-content-ltr\"><div class=\"mw-parser-output\"><div class=\"homonymie bandeau-entete-label\"><a href=\"/wiki/Wikip%C3%A9dia:Articles_de_qualit%C3%A9\" title=\"Wikipédia:Articles de qualité\"><img alt=\"Wikipédia:Articles de qualité\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/6/66/Fairytale_bookmark_gold.svg/15px-Fairytale_bookmark_gold.svg.png\" width=\"15\" height=\"15\" class=\"noviewer\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/6/66/Fairytale_bookmark_gold.svg/23px-Fairytale_bookmark_gold.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/6/66/Fairytale_bookmark_gold.svg/30px-Fairytale_bookmark_gold.svg.png 2x\" data-file-width=\"100\" data-file-height=\"100\" /></a>&#160;<i>Vous lisez un «&#160;<a href=\"/wiki/Wikip%C3%A9dia:Articles_de_qualit%C3%A9\" title=\"Wikipédia:Articles de qualité\">article de qualité</a>&#160;».</i></div>\n" +
                "<div class=\"homonymie\"><a href=\"/wiki/Aide:Homonymie\" title=\"Aide:Homonymie\"><img alt=\"Page d'aide sur l'homonymie\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Disambig_colour.svg/20px-Disambig_colour.svg.png\" width=\"20\" height=\"15\" class=\"noviewer\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Disambig_colour.svg/30px-Disambig_colour.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Disambig_colour.svg/40px-Disambig_colour.svg.png 2x\" data-file-width=\"272\" data-file-height=\"200\" /></a>&#160;Pour les articles homonymes, voir <a href=\"/wiki/Vampire_(homonymie)\" class=\"mw-disambig\" title=\"Vampire (homonymie)\">Vampire (homonymie)</a>.</div>\n" +
                "<div class=\"infobox_v3 large\">\n" +
                "<div class=\"entete icon defaut\" style=\"background-color: #c3a860; border: 3px solid #726238; box-sizing:border-box;\">\n" +
                "<div>Vampire</div>\n" +
                "</div>\n" +
                "<div class=\"images\"><a href=\"/wiki/Fichier:Burne-Jones-le-Vampire.jpg\" class=\"image\"><img alt=\"Description de cette image, également commentée ci-après\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Burne-Jones-le-Vampire.jpg/220px-Burne-Jones-le-Vampire.jpg\" width=\"220\" height=\"310\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Burne-Jones-le-Vampire.jpg/330px-Burne-Jones-le-Vampire.jpg 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/b/bc/Burne-Jones-le-Vampire.jpg/440px-Burne-Jones-le-Vampire.jpg 2x\" data-file-width=\"1391\" data-file-height=\"1963\" /></a></div>\n" +
                "<p class=\"legend\">Tableau <i>Le Vampire</i> par Philip Burne-Jones, 1897.</p>\n" +
                "<p class=\"bloc\" style=\"background:#ede5cf; color:#663333;\">Créature</p>\n" +
                "<table>\n" +
                "<tr>\n" +
                "<th scope=\"row\" style=\"width:9em;\">Sous-groupe</th>\n" +
                "<td><a href=\"/wiki/Mort-vivant\" title=\"Mort-vivant\">Mort-vivant</a> (immortel)</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<th scope=\"row\" style=\"width:9em;\">Proches</th>\n" +
                "<td><a href=\"/wiki/Nukekubi\" title=\"Nukekubi\">Nukekubi</a>, <a href=\"/wiki/Pontianak_(folklore)\" title=\"Pontianak (folklore)\">Pontianak</a>, <a href=\"/wiki/Vrykolakas\" title=\"Vrykolakas\">Vrykolakas</a>, Latawiec, <a href=\"/wiki/Dhampire\" title=\"Dhampire\">Dhampire</a>, Asanbosam...</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<p class=\"bloc\" style=\"background:#ede5cf; color:#663333;\">Origines</p>\n" +
                "<table>\n" +
                "<tr>\n" +
                "<th scope=\"row\" style=\"width:9em;\">Origine</th>\n" +
                "<td>diverses</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<p class=\"navbar bordered noprint\" style=\"border-color:#ede5cf;\"><span class=\"plainlinks\"><a class=\"external text\" href=\"//fr.wikipedia.org/w/index.php?title=Vampire&amp;action=edit&amp;section=0\">modifier</a></span> <a href=\"/wiki/Mod%C3%A8le:Infobox_Cr%C3%A9ature\" title=\"Consultez la documentation du modèle\"><img alt=\"Consultez la documentation du modèle\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Gtk-dialog-info.svg/12px-Gtk-dialog-info.svg.png\" width=\"12\" height=\"12\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Gtk-dialog-info.svg/18px-Gtk-dialog-info.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Gtk-dialog-info.svg/24px-Gtk-dialog-info.svg.png 2x\" data-file-width=\"60\" data-file-height=\"60\" /></a></p>\n" +
                "</div>\n" +
                "<p>Le <b>vampire</b> est un type de revenant qui fait partie des grandes <a href=\"/wiki/Liste_de_cr%C3%A9atures_l%C3%A9gendaires\" title=\"Liste de créatures légendaires\">créatures légendaires</a> issues des mythologies où se combinent de diverses manières l'inquiétude de l'<a href=\"/wiki/Au-del%C3%A0\" title=\"Au-delà\">au-delà</a> et le mystère du sang. Suivant différents folklores et selon la superstition la plus courante, ce <a href=\"/wiki/Mort-vivant\" title=\"Mort-vivant\">mort-vivant</a> se nourrit du sang des vivants afin d’en tirer sa force vitale, ses victimes devenant après leur mort des vampires. La légende du vampire puise ses origines dans des traditions <a href=\"/wiki/Mythologie\" title=\"Mythologie\">mythologiques</a> anciennes et diverses, elle se retrouve dans toutes sortes de cultures à travers le monde.</p>\n" +
                "<p>Le personnage du vampire est popularisé en Europe au début du <abbr class=\"abbr\" title=\"18ᵉ siècle\"><span class=\"romain\">XVIII</span><sup style=\"font-size:72%;line-height:normal;\">e</sup></abbr>&#160;siècle. Vers 1725, le mot «&#160;vampire&#160;» apparaît dans les légendes d'<a href=\"/wiki/Arnold_Paole\" title=\"Arnold Paole\">Arnold Paole</a> et de <a href=\"/wiki/Peter_Plogojowitz\" title=\"Peter Plogojowitz\">Peter Plogojowitz</a>, deux <a href=\"/wiki/Confins_militaires\" title=\"Confins militaires\">soldats autrichiens</a> qui, lors d’une guerre entre l’<a href=\"/wiki/Empire_d%27Autriche\" title=\"Empire d'Autriche\">Empire d'Autriche</a> et l'<a href=\"/wiki/Empire_ottoman\" title=\"Empire ottoman\">Empire ottoman</a>, seraient revenus après leur mort sous forme de vampires, pour hanter les villages de Medvegia et <a href=\"/wiki/Kisiljevo\" title=\"Kisiljevo\">Kisiljevo</a>. Selon ces légendes, les vampires sont dépeints comme des revenants en <a href=\"/wiki/Linceul\" class=\"mw-redirect\" title=\"Linceul\">linceul</a> qui, visitant leurs aimées ou leurs proches, causent mort et désolation. <a href=\"/wiki/Michael_Ranft\" title=\"Michael Ranft\">Michael Ranft</a> écrit un ouvrage, le <i><a href=\"/wiki/De_masticatione_mortuorum_in_tumulis\" title=\"De masticatione mortuorum in tumulis\">De masticatione mortuorum in tumulis</a></i> (1728) dans lequel il examine la croyance dans les vampires. Le revenant y est complètement, et pour la première fois, assimilé à un vampire, puisque Ranft utilise le terme slave de <i>vampyri</i>. Par la suite, le <a href=\"/wiki/B%C3%A9n%C3%A9dictin\" class=\"mw-redirect\" title=\"Bénédictin\">bénédictin</a> lorrain <a href=\"/wiki/Dom_Calmet\" class=\"mw-redirect\" title=\"Dom Calmet\">Augustin Calmet</a> décrit, dans son <i><a href=\"/wiki/Trait%C3%A9_sur_les_apparitions_des_anges,_des_d%C3%A9mons_et_des_esprits_et_sur_les_revenants,_et_vampires_de_Hongrie,_de_Boh%C3%A8me,_de_Moravie_et_de_Sil%C3%A9sie\" class=\"mw-redirect\" title=\"Traité sur les apparitions des anges, des démons et des esprits et sur les revenants, et vampires de Hongrie, de Bohème, de Moravie et de Silésie\">Traité sur les apparitions</a></i> (1751), le vampire comme un «&#160;revenant en corps&#160;», le distinguant ainsi des revenants immatériels tels que les <a href=\"/wiki/Strigoi\" title=\"Strigoi\">stryges</a>, <a href=\"/wiki/Fant%C3%B4me\" title=\"Fantôme\">fantômes</a> et autres esprits.</p>\n" +
                "<p>Diverses explications sont avancées au fil du temps pour expliquer l'universalité du mythe du vampire, entre autres les phénomènes de <a href=\"/wiki/D%C3%A9composition\" title=\"Décomposition\">décomposition</a> des <a href=\"/wiki/Cadavre\" title=\"Cadavre\">cadavres</a>, les <a href=\"/wiki/Enterrement_vivant\" title=\"Enterrement vivant\">enfouissements vivants</a>, des maladies telles que la <a href=\"/wiki/Tuberculose\" class=\"mw-redirect\" title=\"Tuberculose\">tuberculose</a>, la <a href=\"/wiki/Rage_(maladie)\" title=\"Rage (maladie)\">rage</a> et la <a href=\"/wiki/Porphyrie\" title=\"Porphyrie\">porphyrie</a>, ou encore le <a href=\"/wiki/Vampirisme_clinique\" title=\"Vampirisme clinique\">vampirisme clinique</a> affectant les <a href=\"/wiki/Tueur_en_s%C3%A9rie\" title=\"Tueur en série\">tueurs en série</a> qui consomment du sang humain. Des explications scientifiques, psychanalytiques ou encore sociologiques tentent de cerner la raison qui fait que le mythe du vampire perdure à travers les siècles et les civilisations.</p>\n" +
                "<p>Le personnage charismatique et sophistiqué du vampire des fictions modernes apparaît avec la publication en 1819 du livre <i>The Vampyre</i> de <a href=\"/wiki/John_Polidori\" title=\"John Polidori\">John Polidori</a>, dont le héros mort-vivant est inspiré par <a href=\"/wiki/George_Gordon_Byron\" class=\"mw-redirect\" title=\"George Gordon Byron\">Lord Byron</a>, Polidori étant son médecin personnel. Le livre remporte un grand succès mais c'est surtout l'ouvrage de <a href=\"/wiki/Bram_Stoker\" title=\"Bram Stoker\">Bram Stoker</a> paru en <a href=\"/wiki/1897\" title=\"1897\">1897</a>, <i><a href=\"/wiki/Dracula\" title=\"Dracula\">Dracula</a></i>, qui reste la quintessence du genre, établissant une image du vampire toujours populaire de nos jours dans les ouvrages de fiction, même s'il est assez éloigné de ses ancêtres folkloriques avec lesquels il ne conserve que peu de points communs.</p>\n" +
                "<p>Avec le cinéma, le vampire moderne est devenu une figure incontournable, aussi bien dans le domaine de la <a href=\"/wiki/Vampire_dans_la_litt%C3%A9rature\" class=\"mw-redirect\" title=\"Vampire dans la littérature\">littérature</a> que de celui des <a href=\"/wiki/Vampire_dans_les_jeux_vid%C3%A9o\" title=\"Vampire dans les jeux vidéo\">jeux vidéo</a>, des <a href=\"/wiki/Vampire_dans_les_jeux_de_r%C3%B4le\" title=\"Vampire dans les jeux de rôle\">jeux de rôle</a>, <a href=\"/wiki/Vampire_dans_la_bande_dessin%C3%A9e_et_l%27anime\" title=\"Vampire dans la bande dessinée et l'anime\">de l'animation ou encore de la bande dessinée</a>. La croyance en ces créatures perdure et se poursuit aussi bien dans le folklore populaire que par des sous-cultures, notamment <a href=\"/wiki/Mouvement_gothique\" title=\"Mouvement gothique\">gothiques</a>, qui s'y identifient.</p>\n" +
                "<p></p>\n" +
                "<div id=\"toc\" class=\"toc\">\n" +
                "<div id=\"toctitle\" class=\"toctitle\">\n" +
                "<h2>Sommaire</h2>\n" +
                "</div>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-1 tocsection-1\"><a href=\"#Origine_du_mot_.C2.AB_vampire_.C2.BB\"><span class=\"tocnumber\">1</span> <span class=\"toctext\">Origine du mot «&#160;vampire&#160;»</span></a></li>\n" +
                "<li class=\"toclevel-1 tocsection-2\"><a href=\"#Caract.C3.A9ristiques\"><span class=\"tocnumber\">2</span> <span class=\"toctext\">Caractéristiques</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-3\"><a href=\"#Transformation_en_vampire\"><span class=\"tocnumber\">2.1</span> <span class=\"toctext\">Transformation en vampire</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-4\"><a href=\"#Identification\"><span class=\"tocnumber\">2.2</span> <span class=\"toctext\">Identification</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-5\"><a href=\"#Facult.C3.A9s\"><span class=\"tocnumber\">2.3</span> <span class=\"toctext\">Facultés</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-6\"><a href=\"#Protection_contre_le_vampire\"><span class=\"tocnumber\">3</span> <span class=\"toctext\">Protection contre le vampire</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-7\"><a href=\"#Pr.C3.A9cautions_au_d.C3.A9c.C3.A8s_et_.C3.A0_l.27inhumation\"><span class=\"tocnumber\">3.1</span> <span class=\"toctext\">Précautions au décès et à l'inhumation</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-8\"><a href=\"#Objets_et_lieux_apotropa.C3.AFques\"><span class=\"tocnumber\">3.2</span> <span class=\"toctext\">Objets et lieux apotropaïques</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-9\"><a href=\"#Destruction_des_vampires\"><span class=\"tocnumber\">4</span> <span class=\"toctext\">Destruction des vampires</span></a></li>\n" +
                "<li class=\"toclevel-1 tocsection-10\"><a href=\"#Liens_avec_le_monde_animal\"><span class=\"tocnumber\">5</span> <span class=\"toctext\">Liens avec le monde animal</span></a></li>\n" +
                "<li class=\"toclevel-1 tocsection-11\"><a href=\"#Liens_avec_le_monde_v.C3.A9g.C3.A9tal\"><span class=\"tocnumber\">6</span> <span class=\"toctext\">Liens avec le monde végétal</span></a></li>\n" +
                "<li class=\"toclevel-1 tocsection-12\"><a href=\"#Cr.C3.A9atures_associ.C3.A9es_aux_vampires\"><span class=\"tocnumber\">7</span> <span class=\"toctext\">Créatures associées aux vampires</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-13\"><a href=\"#En_Europe\"><span class=\"tocnumber\">7.1</span> <span class=\"toctext\">En Europe</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-14\"><a href=\"#En_Afrique\"><span class=\"tocnumber\">7.2</span> <span class=\"toctext\">En Afrique</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-15\"><a href=\"#En_Am.C3.A9rique\"><span class=\"tocnumber\">7.3</span> <span class=\"toctext\">En Amérique</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-16\"><a href=\"#En_Asie\"><span class=\"tocnumber\">7.4</span> <span class=\"toctext\">En Asie</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-17\"><a href=\"#Histoire_du_vampire\"><span class=\"tocnumber\">8</span> <span class=\"toctext\">Histoire du vampire</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-18\"><a href=\"#Mythe_du_vampire_et_premi.C3.A8res_religions\"><span class=\"tocnumber\">8.1</span> <span class=\"toctext\">Mythe du vampire et premières religions</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-19\"><a href=\"#Antiquit.C3.A9_gr.C3.A9co-romaine\"><span class=\"tocnumber\">8.2</span> <span class=\"toctext\">Antiquité gréco-romaine</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-20\"><a href=\"#Du_Moyen_.C3.82ge_.C3.A0_la_Renaissance\"><span class=\"tocnumber\">8.3</span> <span class=\"toctext\">Du Moyen Âge à la Renaissance</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-3 tocsection-21\"><a href=\"#Une_origine_slave\"><span class=\"tocnumber\">8.3.1</span> <span class=\"toctext\">Une origine slave</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-22\"><a href=\"#Le_corpus_vampirique\"><span class=\"tocnumber\">8.3.2</span> <span class=\"toctext\">Le corpus vampirique</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-23\"><a href=\"#Vlad_Tepes_Dracul.2C_.C2.AB_l.27empaleur_.C2.BB\"><span class=\"tocnumber\">8.3.3</span> <span class=\"toctext\">Vlad Tepes Dracul, «&#160;l'empaleur&#160;»</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-24\"><a href=\"#.C3.89lisabeth_B.C3.A1thory.2C_.C2.AB_la_comtesse_sanglante_.C2.BB\"><span class=\"tocnumber\">8.3.4</span> <span class=\"toctext\">Élisabeth Báthory, «&#160;la comtesse sanglante&#160;»</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-25\"><a href=\"#Premiers_cas_c.C3.A9l.C3.A8bres\"><span class=\"tocnumber\">8.3.5</span> <span class=\"toctext\">Premiers cas célèbres</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-2 tocsection-26\"><a href=\"#D.C3.A9veloppement_des_r.C3.A9cits_de_vampire_aux_XVIIe_et_XVIIIe.C2.A0si.C3.A8cles\"><span class=\"tocnumber\">8.4</span> <span class=\"toctext\">Développement des récits de vampire aux <span>XVII</span><sup>e</sup> et <span>XVIII</span><sup>e</sup>&#160;siècles</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-3 tocsection-27\"><a href=\"#Peur_des_vampires_.C3.A0_travers_l.27Europe\"><span class=\"tocnumber\">8.4.1</span> <span class=\"toctext\">Peur des vampires à travers l'Europe</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-28\"><a href=\"#Trait.C3.A9s_de_vampirologie\"><span class=\"tocnumber\">8.4.2</span> <span class=\"toctext\">Traités de vampirologie</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-2 tocsection-29\"><a href=\"#P.C3.A9riode_contemporaine\"><span class=\"tocnumber\">8.5</span> <span class=\"toctext\">Période contemporaine</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-3 tocsection-30\"><a href=\"#Persistance_des_croyances_et_vampirisme_moderne\"><span class=\"tocnumber\">8.5.1</span> <span class=\"toctext\">Persistance des croyances et vampirisme moderne</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-31\"><a href=\"#Cryptozoologie\"><span class=\"tocnumber\">8.5.2</span> <span class=\"toctext\">Cryptozoologie</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-32\"><a href=\"#Criminologie\"><span class=\"tocnumber\">8.5.3</span> <span class=\"toctext\">Criminologie</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-33\"><a href=\"#Explications_du_vampirisme\"><span class=\"tocnumber\">9</span> <span class=\"toctext\">Explications du vampirisme</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-34\"><a href=\"#Ph.C3.A9nom.C3.A8nes_de_d.C3.A9composition.2C_conservation_des_corps\"><span class=\"tocnumber\">9.1</span> <span class=\"toctext\">Phénomènes de décomposition, conservation des corps</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-35\"><a href=\"#Enterrement_pr.C3.A9matur.C3.A9.2C_profanations_des_tombes\"><span class=\"tocnumber\">9.2</span> <span class=\"toctext\">Enterrement prématuré, profanations des tombes</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-36\"><a href=\"#Contagions.2C_maladies_et_.C3.A9pid.C3.A9mies\"><span class=\"tocnumber\">9.3</span> <span class=\"toctext\">Contagions, maladies et épidémies</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-3 tocsection-37\"><a href=\"#Rage\"><span class=\"tocnumber\">9.3.1</span> <span class=\"toctext\">Rage</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-38\"><a href=\"#Porphyrie\"><span class=\"tocnumber\">9.3.2</span> <span class=\"toctext\">Porphyrie</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-2 tocsection-39\"><a href=\"#Explications_psychiatriques\"><span class=\"tocnumber\">9.4</span> <span class=\"toctext\">Explications psychiatriques</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-40\"><a href=\"#Psychanalyse_et_symbolique_du_vampire\"><span class=\"tocnumber\">9.5</span> <span class=\"toctext\">Psychanalyse et symbolique du vampire</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-41\"><a href=\"#Interpr.C3.A9tations_politiques\"><span class=\"tocnumber\">9.6</span> <span class=\"toctext\">Interprétations politiques</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-42\"><a href=\"#En_litt.C3.A9rature\"><span class=\"tocnumber\">10</span> <span class=\"toctext\">En littérature</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-43\"><a href=\"#Premiers_.C3.A9crits_litt.C3.A9raires\"><span class=\"tocnumber\">10.1</span> <span class=\"toctext\">Premiers écrits littéraires</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-44\"><a href=\"#Le_Vampire_de_John_William_Polidori\"><span class=\"tocnumber\">10.2</span> <span class=\"toctext\"><i>Le Vampire</i> de John William Polidori</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-45\"><a href=\"#Engouement_pour_le_mythe\"><span class=\"tocnumber\">10.3</span> <span class=\"toctext\">Engouement pour le mythe</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-46\"><a href=\"#Carmilla_de_Sheridan_Le_Fanu\"><span class=\"tocnumber\">10.4</span> <span class=\"toctext\"><i>Carmilla</i> de Sheridan Le Fanu</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-47\"><a href=\"#Dracula_de_Bram_Stoker\"><span class=\"tocnumber\">10.5</span> <span class=\"toctext\"><i>Dracula</i> de Bram Stoker</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-48\"><a href=\"#R.C3.A9cup.C3.A9rations_modernes_et_amplifications_du_mythe\"><span class=\"tocnumber\">10.6</span> <span class=\"toctext\">Récupérations modernes et amplifications du mythe</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-49\"><a href=\"#Au_cin.C3.A9ma\"><span class=\"tocnumber\">11</span> <span class=\"toctext\">Au cinéma</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-50\"><a href=\"#Les_premiers_films\"><span class=\"tocnumber\">11.1</span> <span class=\"toctext\">Les premiers films</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-51\"><a href=\"#Internationalisation_du_mythe\"><span class=\"tocnumber\">11.2</span> <span class=\"toctext\">Internationalisation du mythe</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-52\"><a href=\"#Sagas_.C3.A0_succ.C3.A8s\"><span class=\"tocnumber\">11.3</span> <span class=\"toctext\">Sagas à succès</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-53\"><a href=\"#.C3.80_la_t.C3.A9l.C3.A9vision\"><span class=\"tocnumber\">12</span> <span class=\"toctext\">À la télévision</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-54\"><a href=\"#S.C3.A9ries\"><span class=\"tocnumber\">12.1</span> <span class=\"toctext\">Séries</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-55\"><a href=\"#S.C3.A9ries_d.27animation\"><span class=\"tocnumber\">12.2</span> <span class=\"toctext\">Séries d'animation</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-56\"><a href=\"#Dans_la_bande_dessin.C3.A9e.2C_le_manga_et_l.27anime\"><span class=\"tocnumber\">13</span> <span class=\"toctext\">Dans la bande dessinée, le manga et l'anime</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-57\"><a href=\"#Dans_le_manga\"><span class=\"tocnumber\">13.1</span> <span class=\"toctext\">Dans le manga</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-58\"><a href=\"#En_bande_dessin.C3.A9e\"><span class=\"tocnumber\">13.2</span> <span class=\"toctext\">En bande dessinée</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-59\"><a href=\"#Dans_les_jeux\"><span class=\"tocnumber\">14</span> <span class=\"toctext\">Dans les jeux</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-60\"><a href=\"#Jeux_vid.C3.A9o\"><span class=\"tocnumber\">14.1</span> <span class=\"toctext\">Jeux vidéo</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-61\"><a href=\"#Jeux_de_r.C3.B4le\"><span class=\"tocnumber\">14.2</span> <span class=\"toctext\">Jeux de rôle</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-62\"><a href=\"#Notes_et_r.C3.A9f.C3.A9rences\"><span class=\"tocnumber\">15</span> <span class=\"toctext\">Notes et références</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-63\"><a href=\"#Notes\"><span class=\"tocnumber\">15.1</span> <span class=\"toctext\">Notes</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-64\"><a href=\"#R.C3.A9f.C3.A9rences\"><span class=\"tocnumber\">15.2</span> <span class=\"toctext\">Références</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-3 tocsection-65\"><a href=\"#Histoire_des_vampires_de_Claude_Lecouteux\"><span class=\"tocnumber\">15.2.1</span> <span class=\"toctext\"><i>Histoire des vampires</i> de Claude Lecouteux</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-66\"><a href=\"#The_Vampire_Encyclopedia_de_Matthew_Bunson\"><span class=\"tocnumber\">15.2.2</span> <span class=\"toctext\"><i>The Vampire Encyclopedia</i> de Matthew Bunson</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-67\"><a href=\"#Vampires.2C_Burial_and_Death:_Folklore_and_Reality_de_Paul_Barber\"><span class=\"tocnumber\">15.2.3</span> <span class=\"toctext\"><i>Vampires, Burial and Death: Folklore and Reality</i> de Paul Barber</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-68\"><a href=\"#Sang_pour_Sang.2C_Le_R.C3.A9veil_des_Vampires_de_Jean_Marigny\"><span class=\"tocnumber\">15.2.4</span> <span class=\"toctext\"><i>Sang pour Sang, Le Réveil des Vampires</i> de Jean Marigny</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-69\"><a href=\"#Annexes\"><span class=\"tocnumber\">16</span> <span class=\"toctext\">Annexes</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-70\"><a href=\"#Articles_connexes\"><span class=\"tocnumber\">16.1</span> <span class=\"toctext\">Articles connexes</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-71\"><a href=\"#Liens_externes\"><span class=\"tocnumber\">16.2</span> <span class=\"toctext\">Liens externes</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-1 tocsection-72\"><a href=\"#Bibliographie_compl.C3.A9mentaire\"><span class=\"tocnumber\">17</span> <span class=\"toctext\">Bibliographie complémentaire</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-2 tocsection-73\"><a href=\"#Ouvrages_anciens\"><span class=\"tocnumber\">17.1</span> <span class=\"toctext\">Ouvrages anciens</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-74\"><a href=\"#Litt.C3.A9rature\"><span class=\"tocnumber\">17.2</span> <span class=\"toctext\">Littérature</span></a></li>\n" +
                "<li class=\"toclevel-2 tocsection-75\"><a href=\"#Ouvrages_modernes\"><span class=\"tocnumber\">17.3</span> <span class=\"toctext\">Ouvrages modernes</span></a>\n" +
                "<ul>\n" +
                "<li class=\"toclevel-3 tocsection-76\"><a href=\"#Encyclop.C3.A9dies_et_dictionnaires\"><span class=\"tocnumber\">17.3.1</span> <span class=\"toctext\">Encyclopédies et dictionnaires</span></a></li>\n" +
                "<li class=\"toclevel-3 tocsection-77\"><a href=\"#Essais\"><span class=\"tocnumber\">17.3.2</span> <span class=\"toctext\">Essais</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "<li class=\"toclevel-2 tocsection-78\"><a href=\"#Articles\"><span class=\"tocnumber\">17.4</span> <span class=\"toctext\">Articles</span></a></li>\n" +
                "</ul>\n" +
                "</li>\n" +
                "</ul>\n" +
                "</div>\n" +
                "<p></p>\n" +
                "<h2><span class=\"mw-headline\" id=\"Origine_du_mot_.C2.AB_vampire_.C2.BB\">Origine du mot «&#160;vampire&#160;»</span><span class=\"mw-editsection\"><span class=\"mw-editsection-bracket\">[</span><a href=\"/w/index.php?title=Vampire&amp;veaction=edit&amp;section=1\" class=\"mw-editsection-visualeditor\" title=\"Modifier la section : Origine du mot « vampire »\">modifier</a><span class=\"mw-editsection-divider\"> | </span><a href=\"/w/index.php?title=Vampire&amp;action=edit&amp;section=1\" title=\"Modifier la section : Origine du mot « vampire »\">modifier le code</a><span class=\"mw-editsection-bracket\">]</span></span></h2>\n" +
                "<p>Le mot attribué pour désigner les vampires varie d'une langue à l'autre, de même que les attributs et caractéristiques attachés à la créature. Selon l'<span class=\"lang-en\" lang=\"en\"><i><a href=\"/wiki/Oxford_English_Dictionary\" title=\"Oxford English Dictionary\">Oxford English Dictionary</a></i></span>, le mot «&#160;vampire&#160;» apparaît dans la <a href=\"/wiki/Langue_anglaise\" class=\"mw-redirect\" title=\"Langue anglaise\">langue anglaise</a> en 1734, dans un ouvrage de voyage intitulé <i><span class=\"lang-en\" lang=\"en\">Travels of Three English Gentlemen</span></i>, publié dans le <i><span class=\"lang-en\" lang=\"en\">Harleian Miscellany</span></i> de 1745<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\"><span class=\"cite_crochet\">[</span>1<span class=\"cite_crochet\">]</span></a></sup>. C'est par la langue anglaise qu'il se répand dans le monde, via la littérature puis le cinéma. Cependant, le terme anglais est originellement dérivé du mot français «&#160;vampyre&#160;», provenant lui-même de l'allemand «&#160;<i>vampir</i>&#160;»<sup id=\"cite_ref-Barber5_2-0\" class=\"reference\"><a href=\"#cite_note-Barber5-2\"><span class=\"cite_crochet\">[</span>D 1<span class=\"cite_crochet\">]</span></a></sup>, introduit au <abbr class=\"abbr\" title=\"18ᵉ siècle\"><span class=\"romain\">XVIII</span><sup style=\"font-size:72%;line-height:normal;\">e</sup></abbr>&#160;siècle par la forme serbo-croate «&#160;вампир&#160;»/«&#160;<i>vāmpῑr.</i>&#160;»<sup id=\"cite_ref-Grimm_3-0\" class=\"reference\"><a href=\"#cite_note-Grimm-3\"><span class=\"cite_crochet\">[</span>2<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-4\" class=\"reference\"><a href=\"#cite_note-4\"><span class=\"cite_crochet\">[</span>3<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-5\" class=\"reference\"><a href=\"#cite_note-5\"><span class=\"cite_crochet\">[</span>4<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-6\" class=\"reference\"><a href=\"#cite_note-6\"><span class=\"cite_crochet\">[</span>5<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-7\" class=\"reference\"><a href=\"#cite_note-7\"><span class=\"cite_crochet\">[</span>6<span class=\"cite_crochet\">]</span></a></sup>. En France, le <i>Nouveau Larousse illustré</i> de 1900 est le premier dictionnaire à définir les vampires comme étant <span class=\"citation\">«&#160;des morts qui sortent de leur tombeau, de préférence la nuit, pour tourmenter les vivants, le plus souvent en les suçant au cou, d'autres fois en les serrant à la gorge au point de les étouffer<sup id=\"cite_ref-8\" class=\"reference\"><a href=\"#cite_note-8\"><span class=\"cite_crochet\">[</span>7<span class=\"cite_crochet\">]</span></a></sup>&#160;»</span>. C'est, semble-t-il, <a href=\"/wiki/Arnold_Paole\" title=\"Arnold Paole\">Arnold Paole</a>, un supposé vampire de <a href=\"/wiki/Serbie\" title=\"Serbie\">Serbie</a>, qui est le premier à être dénommé «&#160;vampire&#160;», terme apparu lors de l'annexion de la Serbie à l'<a href=\"/wiki/Archiduch%C3%A9_d%27Autriche\" title=\"Archiduché d'Autriche\">Autriche</a>. Après que Vienne eut obtenu le contrôle du Nord de la Serbie et de l'<a href=\"/wiki/Oltenie\" class=\"mw-redirect\" title=\"Oltenie\">Oltenie</a>, par le <a href=\"/wiki/Trait%C3%A9_de_Passarowitz\" title=\"Traité de Passarowitz\">traité de Passarowitz</a>, en 1718, des rapports officiels évoquent des pratiques locales d'exhumation des corps et de meurtres de supposés vampires<sup id=\"cite_ref-Barber5_2-1\" class=\"reference\"><a href=\"#cite_note-Barber5-2\"><span class=\"cite_crochet\">[</span>D 1<span class=\"cite_crochet\">]</span></a></sup>. Ces rapports écrits, qui s'étalent de 1725 à 1732, connaissent un grand écho dans la presse d'alors<sup id=\"cite_ref-Barber5_2-2\" class=\"reference\"><a href=\"#cite_note-Barber5-2\"><span class=\"cite_crochet\">[</span>D 1<span class=\"cite_crochet\">]</span></a></sup>. C'est en effet la forme slave qui est l'<a href=\"/wiki/%C3%89tymologie\" title=\"Étymologie\">étymologie</a> la plus probable des termes européens. Le <a href=\"/wiki/Mot\" title=\"Mot\">vocable</a> slave désignant les revenants a été par la suite systématiquement rendu par le mot «&#160;vampire&#160;»<sup id=\"cite_ref-9\" class=\"reference\"><a href=\"#cite_note-9\"><span class=\"cite_crochet\">[</span>A 1<span class=\"cite_crochet\">]</span></a></sup>.</p>\n" +
                "<p>D'après Vasmer, qui fait autorité en matière d'étymologie des langues russe et slaves, le mot d'origine est le mot \"upir' existant dans toutes les langues slaves (en <a href=\"/wiki/Bulgare\" title=\"Bulgare\">bulgare</a>&#160;: «&#160;въпир&#160;», en <a href=\"/wiki/Croate\" title=\"Croate\">croate</a>&#160;: «&#160;<i>upir</i>&#160;» /«&#160;<i>upirina</i>&#160;», en <a href=\"/wiki/Tch%C3%A8que\" title=\"Tchèque\">tchèque</a> et <a href=\"/wiki/Slovaque\" title=\"Slovaque\">slovaque</a>&#160;: «&#160;<i>upír</i>&#160;», en <a href=\"/wiki/Polonais\" title=\"Polonais\">polonais</a>&#160;: «&#160;<i>upior, wapierz&#160;»</i> , issu de la légende de Łuc Zak, en <a href=\"/wiki/Ukrainien\" title=\"Ukrainien\">ukrainien</a>&#160;: «&#160;упир&#160;» («&#160;<i>upyr</i>&#160;»), en <a href=\"/wiki/Russe\" title=\"Russe\">russe</a>&#160;: «&#160;упырь&#160;» («&#160;<i>upyr'</i>&#160;») et en biélorusse&#160;: «&#160;упыр&#160;» («&#160;<i>upyr</i>&#160;»). L'auteur reconstruit la forme slave commune en \"Ọpyr\" ou \"Ợpir\". La forme \"vampir\" pourrait provenir du polabe ou du vieux polonais. Vasmer réfute l'origine tatare comme l'origine à partir du vieil indien<sup id=\"cite_ref-Vasmer_10-0\" class=\"reference\"><a href=\"#cite_note-Vasmer-10\"><span class=\"cite_crochet\">[</span>8<span class=\"cite_crochet\">]</span></a></sup>.</p>\n" +
                "<p>C'est un <a href=\"/wiki/Colophon_(imprimerie)\" class=\"mw-redirect\" title=\"Colophon (imprimerie)\">colophon</a> dans un manuscrit du <i><a href=\"/wiki/Livre_des_Psaumes\" title=\"Livre des Psaumes\">Livre des Psaumes</a></i> écrit par un prêtre qui l'a traduit du <a href=\"/wiki/Glagolitique\" class=\"mw-redirect\" title=\"Glagolitique\">glagolitique</a> en <a href=\"/wiki/Cyrillique\" class=\"mw-redirect\" title=\"Cyrillique\">cyrillique</a> pour le prince Volodymyr Yaroslavovych. Le prêtre écrit en effet que son nom est «&#160;<i>Upir' Likhyi</i>&#160;» («&#160;Оупирь Лихыи&#160;»), terme qui signifie «&#160;mauvais vampire&#160;»<sup id=\"cite_ref-11\" class=\"reference\"><a href=\"#cite_note-11\"><span class=\"cite_crochet\">[</span>9<span class=\"cite_crochet\">]</span></a></sup>. Ce nom étrange semble avoir survécu dans des pratiques <a href=\"/wiki/Paganisme\" title=\"Paganisme\">païennes</a> mais aussi dans des prénoms ou surnoms<sup id=\"cite_ref-12\" class=\"reference\"><a href=\"#cite_note-12\"><span class=\"cite_crochet\">[</span>10<span class=\"cite_crochet\">]</span></a></sup>. Un autre <a href=\"/wiki/Mot\" title=\"Mot\">vocable</a> provenant de l'ancien russe, «&#160;<i>upyri</i>&#160;», apparaît dans un traité anti-païen intitulé <i>Mot de Saint Grégoire</i>, daté entre les <abbr class=\"abbr\" title=\"11ᵉ siècle\"><span class=\"romain\">XI</span><sup style=\"font-size:72%;line-height:normal;\">e</sup></abbr> et <abbr class=\"abbr\" title=\"13ᵉ siècle\"><span class=\"romain\">XIII</span><sup style=\"font-size:72%;line-height:normal;\">e</sup></abbr>&#160;siècles<sup id=\"cite_ref-13\" class=\"reference\"><a href=\"#cite_note-13\"><span class=\"cite_crochet\">[</span>11<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-14\" class=\"reference\"><a href=\"#cite_note-14\"><span class=\"cite_crochet\">[</span>12<span class=\"cite_crochet\">]</span></a></sup>.</p>\n" +
                "<p>Dans la langue <a href=\"/wiki/Albanais\" title=\"Albanais\">albanaise</a>, la définition de la structure du mot Dhempir est exact dans l'unité morphologique et phonologique<sup id=\"cite_ref-15\" class=\"reference\"><a href=\"#cite_note-15\"><span class=\"cite_crochet\">[</span>13<span class=\"cite_crochet\">]</span></a></sup>. Effectivement, la définition veut littéralement dire \"buveur par les dents\": la dent étant \"dhemb\" et boire ou aspirer étant \"pirja\". Le fils d'un vampire est nommé «&#160;<i>Dhempir</i>&#160;», et une fille de vampire est appelée une «&#160;<i>Dhempiresa</i>&#160;»<sup id=\"cite_ref-16\" class=\"reference\"><a href=\"#cite_note-16\"><span class=\"cite_crochet\">[</span>14<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-17\" class=\"reference\"><a href=\"#cite_note-17\"><span class=\"cite_crochet\">[</span>15<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-18\" class=\"reference\"><a href=\"#cite_note-18\"><span class=\"cite_crochet\">[</span>16<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-19\" class=\"reference\"><a href=\"#cite_note-19\"><span class=\"cite_crochet\">[</span>17<span class=\"cite_crochet\">]</span></a></sup><sup class=\"reference cite_virgule\">,</sup><sup id=\"cite_ref-20\" class=\"reference\"><a href=\"#cite_note-20\"><span class=\"cite_crochet\">[</span>18<span class=\"cite_crochet\">]</span></a></sup>.</p>\n" +
                "<p>Dans le folklore <a href=\"/wiki/Bulgare\" title=\"Bulgare\">bulgare</a>, de nombreux termes tels que «&#160;<i>Glog</i>&#160;» (littéralement&#160;: «&#160;aubépine&#160;»), «&#160;<i>vampirdzhiya</i>&#160;», «&#160;<i>vampirar</i>&#160;», «&#160;<i>dzhadadzhiya</i>&#160;» et «&#160;<i>svetocher</i>&#160;» sont utilisés pour désigner les enfants et les descendants de vampires, ainsi que, à l'inverse, des personnes chassant les vampires<sup id=\"cite_ref-21\" class=\"reference\"><a href=\"#cite_note-21\"><span class=\"cite_crochet\">[</span>19<span class=\"cite_crochet\">]</span></a></sup>.</p>\n" +
                "<h2><span class=\"mw-headline\" id=\"Caract.C3.A9ristiques\">Caractéristiques</span><span class=\"mw-editsection\"><span class=\"mw-editsection-bracket\">[</span><a href=\"/w/index.php?title=Vampire&amp;veaction=edit&amp;section=2\" class=\"mw-editsection-visualeditor\" title=\"Modifier la section : Caractéristiques\">modifier</a><span class=\"mw-editsection-divider\"> | </span><a href=\"/w/index.php?title=Vampire&amp;action=edit&amp;section=2\" title=\"Modifier la section : Caractéristiques\">modifier le code</a><span class=\"mw-editsection-bracket\">]</span></span></h2>\n" +
                "<div class=\"thumb tleft\">\n" +
                "<div class=\"thumbinner\" style=\"width:222px;\"><a href=\"/wiki/Fichier:Varney_the_Vampire_-_Rymer_James_Malcolm.jpg\" class=\"image\"><img alt=\"\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Varney_the_Vampire_-_Rymer_James_Malcolm.jpg/220px-Varney_the_Vampire_-_Rymer_James_Malcolm.jpg\" width=\"220\" height=\"280\" class=\"thumbimage\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Varney_the_Vampire_-_Rymer_James_Malcolm.jpg/330px-Varney_the_Vampire_-_Rymer_James_Malcolm.jpg 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Varney_the_Vampire_-_Rymer_James_Malcolm.jpg/440px-Varney_the_Vampire_-_Rymer_James_Malcolm.jpg 2x\" data-file-width=\"1572\" data-file-height=\"2000\" /></a>\n" +
                "<div class=\"thumbcaption\">\n" +
                "<div class=\"magnify\"><a href=\"/wiki/Fichier:Varney_the_Vampire_-_Rymer_James_Malcolm.jpg\" class=\"internal\" title=\"Agrandir\"></a></div>\n" +
                "<center>Le vampire est actif la nuit et mord le plus souvent ses victimes durant leur sommeil<br />\n" +
                "(<i>Varney the Vampire</i>, gravure, 1847).</center>\n";

        ArrayList<String> pages = new PageCutter(html).getFirstChunk();

        Assert.assertTrue(pages.size() == 2);

        Assert.assertTrue(pages.get(0).length() < 1000);


        html = "<body class=\"mediawiki ltr sitedir-ltr mw-hide-empty-elt ns-0 ns-subject page-Vampire rootpage-Vampire skin-vector action-view\">\t\t<div id=\"mw-page-base\" class=\"noprint\"></div>\n" +
                "\" +\n" +
                "                \"\\t\\t<div id=\\\"mw-head-base\\\" class=\\\"noprint\\\"></div>\\n\" +\n" +
                "                \"\\t\\t<div id=\\\"content\\\" class=\\\"mw-body\\\" role=\\\"main\\\">\\n\" +\n" +
                "                \"\\t\\t\\t<a id=\\\"top\\\"></a>\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\t\\t\\t\\t\\t\\t\\t<div id=\\\"siteNotice\\\" class=\\\"mw-body-content\\\"><!-- CentralNotice --></div>\\n\" +\n" +
                "                \"\\t\\t\\t\\t\\t\\t<div class=\\\"mw-indicators mw-body-content\\\">\\n\" +\n" +
                "                \"</div>\\n\" +\n";

        pages = new PageCutter(html).getFirstChunk();

        Assert.assertTrue(pages.size() == 1);

        Assert.assertTrue(pages.get(0).length() < 1000);
    }
}

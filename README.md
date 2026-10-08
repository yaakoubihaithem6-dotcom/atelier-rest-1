# Atelier REST n°1 - Service web RESTful (Gestion des étudiants et des options)

Projet Maven (packaging `war`) exposant des ressources REST (JAX-RS / Jersey) pour gérer des **options** et des **étudiants** (données en mémoire).

## Technologies
- Java 17, Maven (war)
- JAX-RS avec Jersey 2.27 (JSON via Jackson, XML via JAXB)
- Apache Tomcat 8.5

## Lancer le projet
1. Ouvrir le projet dans IntelliJ IDEA.
2. Maven : `clean` puis `package`.
3. Lancer Tomcat avec l'artifact `Gestion_UE_VF:war exploded`.
4. URL de base : `http://localhost:8081/Gestion_UE_VF_war_exploded/rest`

## Configuration JAX-RS
La classe `utilities.RestActivator` (`@ApplicationPath("rest")`) active les ressources REST.

## Ressources

### Option (`/options`)
| Méthode | URI | Statuts |
|---|---|---|
| POST | `/options` | 200 / 404 |
| GET | `/options` | 200 |
| GET | `/options?domaine=...` | 200 |
| GET | `/options/{code}` | 200 / 404 |
| PUT | `/options/{code}` | 200 / 404 |
| DELETE | `/options/{code}` | 204 / 404 |

### Etudiant (`/etudiants`)
| Méthode | URI | Statuts |
|---|---|---|
| POST | `/etudiants` | 200 / 404 (option inexistante) |
| GET | `/etudiants` | 200 |
| GET | `/etudiants/{identifiant}` | 200 / 404 |
| PUT | `/etudiants/{identifiant}` | 200 / 404 |
| DELETE | `/etudiants/{identifiant}` | 204 / 404 |
| GET | `/etudiants/option?codeOption=1` | 200 (XML) / 404 |

## Tests Postman (captures d'écran)

### Option
| Test | Capture |
|---|---|
| POST /options | ![](screenshots/01_post_option.png) |
| GET /options | ![](screenshots/02_get_options.png) |
| GET /options?domaine=Mathématiques | ![](screenshots/03_get_options_domaine.png) |
| GET /options/1 | ![](screenshots/04_get_option_1.png) |
| GET /options/99 (404) | ![](screenshots/05_get_option_99_404.png) |
| PUT /options/1 | ![](screenshots/06_put_option_1.png) |
| DELETE /options/2 (204) | ![](screenshots/07_delete_option_2_204.png) |
| DELETE /options/2 (404) | ![](screenshots/08_delete_option_2_404.png) |

### Etudiant
| Test | Capture |
|---|---|
| POST /etudiants | ![](screenshots/09_post_etudiant.png) |
| POST /etudiants, option inexistante (404) | ![](screenshots/10_post_etudiant_404.png) |
| GET /etudiants | ![](screenshots/11_get_etudiants.png) |
| GET /etudiants/I001 | ![](screenshots/12_get_etudiant_I001.png) |
| GET /etudiants/I9999 (404) | ![](screenshots/13_get_etudiant_404.png) |
| PUT /etudiants/I001 | ![](screenshots/14_put_etudiant.png) |
| DELETE /etudiants/I003 (204) | ![](screenshots/15_delete_etudiant_204.png) |
| DELETE /etudiants/I003 (404) | ![](screenshots/16_delete_etudiant_404.png) |
| GET /etudiants/option?codeOption=1 (XML) | ![](screenshots/17_get_etudiants_option_xml.png) |
| GET /etudiants/option?codeOption=99 (404) | ![](screenshots/18_get_etudiants_option_404.png) |

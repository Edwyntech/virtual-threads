package com.edwyn;

public class UserController {


    //private final UserService userService = new UserService();
    // Méthode pour récupérer un utilisateur de manière synchrone
/*        @GetMapping("/users/sync/{id}")
        public ResponseEntity<?> getUserSync(@PathVariable int id) {
            try {
                // Récupération de l'utilisateur par ID
                User user = userService.getUserByIdSync(id);

                // Vérifier si l'utilisateur est actif
                if (!user.isActive()) {
                    return ResponseEntity.status(HttpStatus.GONE)
                            .body("User is no longer active");
                }

                // Récupération des détails de l'utilisateur
                String userDetails = userService.getUserDetailsSync();
                user.setUserDetails(userDetails);

                // Retourner la réponse avec les détails de l'utilisateur
                return ResponseEntity.ok(user);
            } catch (UserNotFoundException e) {
                // Gestion de l'exception si l'utilisateur n'est pas trouvé
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            } catch (Exception e) {
                // Gestion de toute autre exception
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An error occurred while fetching the user");
            }
        }*/

    // Méthode pour récupérer un utilisateur de manière réactive
 /*       @GetMapping("/users/reactive/{id}")
        public Mono<ResponseEntity<?>> getUserReactive(@PathVariable int id) {
            // Récupération réactive de l'utilisateur par ID
            Mono<User> userMono = userService.getUserById(id)
                    .filter(user -> user.isActive())
                    .switchIfEmpty(Mono.error(new UserNotActiveException()));

            // Récupération réactive des détails de l'utilisateur
            Mono<String> userDetailsMono = userService.getUserDetails();

            // Combinaison des résultats
            return Mono.zip(userMono, userDetailsMono)
                    .flatMap(tuple -> {
                        User user = tuple.getT1();
                        String details = tuple.getT2();
                        user.setUserDetails(details);
                        return Mono.just(ResponseEntity.ok(user));
                    })
                    .onErrorResume(throwable -> {
                        if (throwable instanceof UserNotFoundException) {
                            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body(throwable.getMessage()));
                        } else if (throwable instanceof UserNotActiveException) {
                            return Mono.just(ResponseEntity.status(HttpStatus.GONE)
                                    .body("User is no longer active"));
                        } else {
                            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("An error occurred while fetching the user"));
                        }
                    });
        }
    }*/

}

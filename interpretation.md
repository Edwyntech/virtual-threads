Résultats des Benchmarks benchmarkAPiPlatformThreads:·stack
## VirtualThreads
* WAITING : 40,5%
* TIMED_WAITING : 35,4%
* RUNNABLE : 24,1%

## PlatformThreads
* WAITING : 58,4%
* TIMED_WAITING : 24,8%
* RUNNABLE : 16,8%
## Analyse et Comparaison
### WAITING

VirtualThreads : 40,5%
PlatformThreads : 58,4%
Comparaison : Les VirtualThreads passent moins de temps en état WAITING comparé aux PlatformThreads. Cela peut indiquer que les VirtualThreads sont moins bloqués par des conditions de synchronisation ou des attentes indéfinies.

### TIMED_WAITING
VirtualThreads : 35,4%
PlatformThreads : 24,8%
Comparaison : Les VirtualThreads passent plus de temps en état TIMED_WAITING que les PlatformThreads. Cela pourrait signifier que les VirtualThreads utilisent plus fréquemment des temporisations ou des attentes avec des délais spécifiques.

### RUNNABLE
VirtualThreads : 24,1%
PlatformThreads : 16,8%
Comparaison : Les VirtualThreads ont une proportion plus élevée de threads en état RUNNABLE, ce qui signifie qu'ils sont plus souvent prêts à s'exécuter ou en cours d'exécution par rapport aux PlatformThreads. Cela peut suggérer une meilleure utilisation du CPU.
Conclusions
Utilisation des ressources

Les VirtualThreads semblent avoir une meilleure utilisation des ressources CPU, comme l'indique le pourcentage plus élevé de threads en état RUNNABLE (24,1% vs 16,8%). Cela signifie qu'ils sont plus actifs et moins souvent en attente indéfinie.
Gestion des attentes

Les VirtualThreads passent moins de temps en attente indéfinie (WAITING) comparé aux PlatformThreads (40,5% vs 58,4%), ce qui peut indiquer une meilleure gestion de la synchronisation et des blocages.
Utilisation des temporisations

Un pourcentage plus élevé de TIMED_WAITING dans les VirtualThreads (35,4% vs 24,8%) suggère qu'ils utilisent davantage les attentes temporisées. Cela peut être un indicateur d'une meilleure répartition du temps d'attente, évitant les blocages prolongés.
Implications pratiques
VirtualThreads : Semblent plus efficaces en termes de gestion du temps d'attente et de l'utilisation du CPU, ce qui peut les rendre plus adaptés pour des applications nécessitant une haute concurrence et une utilisation intensive des ressources.
PlatformThreads : Ont une proportion plus élevée de threads en attente indéfinie, ce qui peut être moins efficace dans des scénarios de haute concurrence. Ils peuvent être plus sujets aux blocages et à la contention.
En somme, les VirtualThreads semblent offrir une meilleure performance et une utilisation plus efficace des ressources dans ce contexte de benchmark.







import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'annee-academique',
        data: { pageTitle: 'uproSoutenanceEsiApp.anneeAcademique.home.title' },
        loadChildren: () => import('./annee-academique/annee-academique.module').then(m => m.AnneeAcademiqueModule),
      },
      {
        path: 'encadreur',
        data: { pageTitle: 'uproSoutenanceEsiApp.encadreur.home.title' },
        loadChildren: () => import('./encadreur/encadreur.module').then(m => m.EncadreurModule),
      },
      {
        path: 'eleve',
        data: { pageTitle: 'uproSoutenanceEsiApp.eleve.home.title' },
        loadChildren: () => import('./eleve/eleve.module').then(m => m.EleveModule),
      },
      {
        path: 'projet',
        data: { pageTitle: 'uproSoutenanceEsiApp.projet.home.title' },
        loadChildren: () => import('./projet/projet.module').then(m => m.ProjetModule),
      },
      {
        path: 'entreprise',
        data: { pageTitle: 'uproSoutenanceEsiApp.entreprise.home.title' },
        loadChildren: () => import('./entreprise/entreprise.module').then(m => m.EntrepriseModule),
      },
      {
        path: 'note-entreprise-rapport',
        data: { pageTitle: 'uproSoutenanceEsiApp.noteEntrepriseRapport.home.title' },
        loadChildren: () => import('./note-entreprise-rapport/note-entreprise-rapport.module').then(m => m.NoteEntrepriseRapportModule),
      },
      {
        path: 'soutenance',
        data: { pageTitle: 'uproSoutenanceEsiApp.soutenance.home.title' },
        loadChildren: () => import('./soutenance/soutenance.module').then(m => m.SoutenanceModule),
      },
      {
        path: 'jury',
        data: { pageTitle: 'uproSoutenanceEsiApp.jury.home.title' },
        loadChildren: () => import('./jury/jury.module').then(m => m.JuryModule),
      },
      {
        path: 'specialite',
        data: { pageTitle: 'uproSoutenanceEsiApp.specialite.home.title' },
        loadChildren: () => import('./specialite/specialite.module').then(m => m.SpecialiteModule),
      },
      {
        path: 'genie',
        data: { pageTitle: 'uproSoutenanceEsiApp.genie.home.title' },
        loadChildren: () => import('./genie/genie.module').then(m => m.GenieModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

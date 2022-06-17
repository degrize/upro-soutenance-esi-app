import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INoteEntrepriseRapport } from '../note-entreprise-rapport.model';

@Component({
  selector: 'jhi-note-entreprise-rapport-detail',
  templateUrl: './note-entreprise-rapport-detail.component.html',
})
export class NoteEntrepriseRapportDetailComponent implements OnInit {
  noteEntrepriseRapport: INoteEntrepriseRapport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ noteEntrepriseRapport }) => {
      this.noteEntrepriseRapport = noteEntrepriseRapport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGenie } from '../genie.model';

@Component({
  selector: 'jhi-genie-detail',
  templateUrl: './genie-detail.component.html',
})
export class GenieDetailComponent implements OnInit {
  genie: IGenie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ genie }) => {
      this.genie = genie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

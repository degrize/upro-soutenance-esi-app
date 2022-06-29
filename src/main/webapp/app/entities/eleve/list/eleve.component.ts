import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest, Observable } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEleve } from '../eleve.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { EleveService } from '../service/eleve.service';
import { EleveDeleteDialogComponent } from '../delete/eleve-delete-dialog.component';
import { Account } from '../../../core/auth/account.model';
import { AccountService } from '../../../core/auth/account.service';
import { IUser } from '../../../admin/user-management/user-management.model';
import { SoutenanceService } from '../../soutenance/service/soutenance.service';
import { ISoutenance } from '../../soutenance/soutenance.model';

@Component({
  selector: 'jhi-eleve',
  templateUrl: './eleve.component.html',
  styleUrls: ['./eleve.component.scss'],
})
export class EleveComponent implements OnInit {
  account: Account | null = null;
  eleveAccount?: IUser | null;
  eleveSoutenence?: ISoutenance | null;
  eleves?: IEleve[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected eleveService: EleveService,
    private accountService: AccountService,
    private soutenanceService: SoutenanceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.eleveService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<IEleve[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });

    this.accountService.getUser({ login: this.account?.login }).subscribe(
      (res: HttpResponse<IUser>) => this.onSucessUser(res.body),
      (res: HttpResponse<any>) => this.onError()
    );

    this.soutenanceService.getEleveSoutenance({ projetId: this.account?.eleve.projet?.id }).subscribe(
      (res: HttpResponse<ISoutenance>) => this.onSucessSoutenanceEleve(res.body),
      (res: HttpResponse<any>) => this.onError()
    );
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      console.log(this.account?.login);
    });
    this.handleNavigation();
  }

  trackId(_index: number, item: IEleve): number {
    return item.id!;
  }

  delete(eleve: IEleve): void {
    const modalRef = this.modalService.open(EleveDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eleve = eleve;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === ASC;
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IEleve[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/eleve'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.eleves = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  protected onSucessUser(data: IUser | null): void {
    this.eleveAccount = data;
    console.log('DATA USER ELEVE');
    console.log(data?.eleve);
  }

  protected onSucessSoutenanceEleve(data: ISoutenance | null): void {
    this.eleveSoutenence = data;
    console.log('DATA SOUTENANCE ELEVE');
    console.log(data);
  }
}

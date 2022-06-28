import { Component, OnInit, OnDestroy, AfterViewInit, AfterViewChecked } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import ScrollReveal from 'scrollreveal';
// Chart Js
import { Chart, registerables } from 'chart.js';
import { SoutenanceService } from '../entities/soutenance/service/soutenance.service';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IAdminStatistics } from '../entities/enumerations/admin-statistics';
Chart.register(...registerables);

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy, AfterViewChecked {
  account: Account | null = null;

  adminStatistics?: IAdminStatistics | null;

  private readonly destroy$ = new Subject<void>();

  constructor(protected soutenanceService: SoutenanceService, private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    //Les données statistiques
    this.soutenanceService
      .findAdminStatistics({
        page: null,
        size: null,
        sort: null,
      })
      .subscribe({
        next: (res: HttpResponse<IAdminStatistics>) => {
          this.onSuccess(res.body);
        },
        error: () => {
          this.onError();
          // alert("ERRor")
        },
      });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  animationScroll(): void {
    /* ===== SCROLL REVEAL ANIMATION =====*/
    const sr = ScrollReveal({
      origin: 'top',
      distance: '80px',
      duration: 2000,
      reset: true,
    });

    /* SCROLL HOME*/
    sr.reveal('.home__title', {});
    sr.reveal('.home__scroll', { delay: 200 });
    sr.reveal('.home__img', { origin: 'right', delay: 400 });

    /* SCROLL ABOUT*/
    sr.reveal('.about__img', { delay: 500 });
    sr.reveal('.about__subtitle', { delay: 300 });
    sr.reveal('.about__profession', { delay: 400 });
    sr.reveal('.about__text', { delay: 500 });
    sr.reveal('.about__social-icon', { delay: 600, interval: 200 });

    /* SCROLL SKILLS*/
    sr.reveal('.skills__subtitle', {});
    sr.reveal('.skills__name', { distance: '20px', delay: 50, interval: 100 });
    sr.reveal('.skills__img', { delay: 400 });

    /* SCROLL PORTFOLIO*/
    sr.reveal('.portfolio__img', { interval: 200 });

    /* SCROLL CONTACT*/
    sr.reveal('.contact__subtitle', {});
    sr.reveal('.contact__text', { interval: 200 });
    sr.reveal('.contact__input', { delay: 400 });
    sr.reveal('.contact__button', { delay: 600 });
  }

  chartJsFunction(): void {
    const mentionAnalyse = new Chart('mentionAnalyse', {
      type: 'polarArea',
      data: {
        labels: ['Très bien', 'Bien', 'Assez bien', 'Passable'],
        datasets: [
          {
            label: 'Mention Soutenance',
            data: [
              this.adminStatistics?.nbreMentionTresBien,
              this.adminStatistics?.nbreMentionBien,
              this.adminStatistics?.nbreMentionAssezBien,
              this.adminStatistics?.nbreMentionPassable,
            ],
            backgroundColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
          },
        ],
      },
      options: {
        responsive: true,
      },
    });

    const vagueSoutenance = new Chart('vagueSoutenance', {
      type: 'bar',
      data: {
        labels: ['Juillet', 'Octobre', 'Fevrier', 'Mars'],
        datasets: [
          {
            label: 'Vagues de Soutenance',
            data: [
              this.adminStatistics?.nbreSoutenuJuillet,
              this.adminStatistics?.nbreSoutenuOctobre,
              this.adminStatistics?.nbreSoutenuFevrier,
              this.adminStatistics?.nbreSoutenuMars,
            ],
            backgroundColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
          },
        ],
      },
      options: {
        responsive: true,
      },
    });
  }

  ngAfterViewChecked(): void {
    this.chartJsFunction(); // on demarre le ChatJs
    this.animationScroll(); // On demmarre les animations
  }

  protected onSuccess(data: IAdminStatistics | null): void {
    this.adminStatistics = data ?? null;
    console.log(this.adminStatistics);
  }

  protected onError(): void {
    console.log('ERROR NO FOUND STATISTICS DATA');
  }
}

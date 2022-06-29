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
import { IUser } from '../admin/user-management/user-management.model';
import { ISoutenance } from '../entities/soutenance/soutenance.model';
Chart.register(...registerables);

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  eleveAccount?: IUser | null;

  eleveSoutenence?: ISoutenance | null;

  adminStatistics?: IAdminStatistics | null;

  private readonly destroy$ = new Subject<void>();

  constructor(protected soutenanceService: SoutenanceService, private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.onSucessAccount(account));

    this.animationScroll(); // On demmarre les animations
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
    if (Chart.getChart('mentionAnalyse')) {
      Chart.getChart('mentionAnalyse')?.destroy();
    } else {
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
    }

    if (Chart.getChart('vagueSoutenance')) {
      Chart.getChart('vagueSoutenance')?.destroy();
    } else {
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
  }

  /* downloadReport():void {
    const applicationType = 'application-pdf';
    const extension = '.pdf';
    const type = 'report';

    // alert(applicationType);
    //const projetId = this.eleveAccount?.eleve?.projet?.id;
    this.soutenanceService.getFilePdf(type, applicationType, "1").subscribe((x: BlobPart) => {
      // It is necessary to create a new blob object with mime-type explicitly set
      // otherwise only Chrome works like it should

      const appType = applicationType.replace('-', '/');
      // alert(appType);
      const newBlob = new Blob([x], { type: appType });

      // IE doesn't allow using a blob object directly as link href
      // instead it is necessary to use msSaveOrOpenBlob
      // if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        // window.navigator.msSaveOrOpenBlob(newBlob);
        // return;
      // }

      // For other browsers:
      // Create a link pointing to the ObjectURL containing the blob.
      const data = window.URL.createObjectURL(newBlob);

      const link = document.createElement('a');
      link.href = data;
      link.download = 'avis-report.pdf' + extension;
      // this is necessary as link.click() does not work on the latest firefox
      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function() {
        // For Firefox it is necessary to delay revoking the ObjectURL
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
    });
  } */

  protected onSuccess(data: IAdminStatistics | null): void {
    this.adminStatistics = data ?? null;
    console.log(this.adminStatistics);
    this.chartJsFunction(); // on demarre le ChatJs
  }

  protected onError(): void {
    console.log('ERROR NO FOUND STATISTICS DATA');
  }
  protected onSucessUser(data: IUser | null): void {
    this.eleveAccount = data;
    console.log('DATA USER ELEVE');
    console.log(data?.eleve?.projet?.id);

    this.soutenanceService.getEleveSoutenance({ projetId: data?.eleve?.projet?.id }).subscribe(
      (res: HttpResponse<ISoutenance>) => this.onSucessSoutenanceEleve(res.body),
      (res: HttpResponse<any>) => this.onError()
    );
  }

  protected onSucessAccount(data: Account | null): void {
    this.account = data;

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

    this.accountService.getUser({ login: this.account?.login }).subscribe(
      (res: HttpResponse<IUser>) => this.onSucessUser(res.body),
      (res: HttpResponse<any>) => this.onError()
    );

    //console.log("DATA ACCOUNT");
    //console.log(data);
  }

  protected onSucessSoutenanceEleve(data: ISoutenance | null): void {
    this.eleveSoutenence = data;
    console.log('DATA SOUTENANCE ELEVE');
    console.log(data);
  }
}

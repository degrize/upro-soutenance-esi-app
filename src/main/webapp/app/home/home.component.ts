import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import ScrollReveal from 'scrollreveal';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.animationScroll();
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
}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];

  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.entitiesNavbarItems = EntityNavbarItems;
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.openAPIEnabled = profileInfo.openAPIEnabled;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.make_design();
    this.nav_dashoardScript();
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  make_design(): void {
    /* ===== MENU SHOW ===== */
    const showMenu = (toggleId: string, navId: string): void => {
      const toggle = document.getElementById(toggleId),
        nav = document.getElementById(navId);

      if (toggle && nav) {
        toggle.addEventListener('click', () => {
          nav.classList.toggle('show');
        });
      }
    };
    showMenu('nav-toggle', 'nav-menu');

    /* ===== REMOVE MENU MOBILE =====*/
    const navLink = document.querySelectorAll('.nav__link');
    navLink.forEach(n => n.addEventListener('click', this.linkAction));

    /* ===== SCROLL SECTIONS ACTIVE LINK =====*/
    window.addEventListener('scroll', this.scrollActive);
  }

  linkAction(): void {
    const navMenu = document.getElementById('nav-menu');
    if (navMenu) {
      navMenu.classList.remove('show');
    }
  }

  scrollActive(): void {
    const scrollY = window.pageYOffset;

    const sections = document.querySelectorAll('section[id]');
    for (let i = 0; i < sections.length; i++) {
      const current = sections[i] as HTMLElement;

      const sectionHeight = current.offsetHeight;
      const sectionTop = current.offsetTop - 50;
      const sectionId = current.getAttribute('id');
      console.log(sectionId);
      if (sectionId) {
        const selector = '.nav__menu a[href*=' + sectionId.toString() + ']';
        const nav__menu = document.querySelector(selector);
        if (nav__menu) {
          if (scrollY > sectionTop && scrollY <= sectionTop + sectionHeight) {
            nav__menu.classList.add('active');
          } else {
            nav__menu.classList.remove('active');
          }
        }
      }
    }
  }

  nav_dashoardScript(): void {
    const body = document.querySelector('#body_nav_dasboard');
    if (body) {
      const modeToggle = body.querySelector('.mode-toggle');
      const sidebar = body.querySelector('#nav_dashboard');
      const sidebarToggle = body.querySelector('.sidebar-toggle');

      const getMode = localStorage.getItem('mode');
      if (getMode && getMode === 'dark') {
        body.classList.toggle('dark');
      }

      const getStatus = localStorage.getItem('status');
      if (getStatus && getStatus === 'close') {
        if (sidebar) {
          sidebar.classList.toggle('close');
        }
      }

      if (modeToggle) {
        modeToggle.addEventListener('click', () => {
          body.classList.toggle('dark');
          if (body.classList.contains('dark')) {
            localStorage.setItem('mode', 'dark');
          } else {
            localStorage.setItem('mode', 'light');
          }
        });
      }

      if (sidebarToggle) {
        sidebarToggle.addEventListener('click', () => {
          if (sidebar) {
            sidebar.classList.toggle('close');
            if (sidebar.classList.contains('close')) {
              localStorage.setItem('status', 'close');
            } else {
              localStorage.setItem('status', 'open');
            }
          }
        });
      }
    }
  }
}

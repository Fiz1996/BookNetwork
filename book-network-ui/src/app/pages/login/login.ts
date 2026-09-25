import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { AuthenticationRequest } from '../../services/models/authentication-request';
import { authenticate } from '../../services/fn/authentication/authenticate';
import { ApiConfiguration } from '../../services/api-configuration';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  authRequest: AuthenticationRequest = {
    email: '',
    password: ''
  };

  errorMsg: string[] = [];

  constructor(
    private router: Router,
    private http: HttpClient,
    private apiConfiguration: ApiConfiguration
  ) {}

  login(): void {

    this.errorMsg = [];

    authenticate(
      this.http,
      this.apiConfiguration.rootUrl,
      {
        body: this.authRequest
      }
    ).subscribe({
      next: (res) => {

        console.log('Authentication response:', res);

        // We'll add TokenService here next
        // this.tokenService.token = res.body?.token;

        this.router.navigate(['/books']);
      },

      error: (err) => {

        console.error(err);

        if (err.error?.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else if (err.error?.errors) {
          this.errorMsg = err.error.errors;
        } else {
          this.errorMsg = ['Authentication failed'];
        }
      }
    });
  }

  register(): void {
    this.router.navigate(['/register']);
  }
}

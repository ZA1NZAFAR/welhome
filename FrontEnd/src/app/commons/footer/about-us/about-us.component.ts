import { Component } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFacebook, faTwitter, faInstagram } from '@fortawesome/free-brands-svg-icons';

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  standalone: true,
  imports: [FontAwesomeModule],
  styleUrls: ['./about-us.component.scss']
})
export class AboutUsComponent {
  faFacebook = faFacebook;
  faTwitter = faTwitter;
  faInstagram = faInstagram;

}

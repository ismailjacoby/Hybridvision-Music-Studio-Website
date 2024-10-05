import {
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  OnInit,
  ViewChild
} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {Production} from "../../../models/Production";
import {ProductionService} from "../../../services/production.service";
import {Genre} from "../../../models/Genre";
import {LicenseType} from "../../../models/LicenseType";
import {ShoppingCartService} from "../../../services/shopping-cart.service";
import {debounceTime, distinctUntilChanged, Subject} from "rxjs";
import {WishlistService} from "../../../services/wishlist.service";




@Component({
  selector: 'app-beats',
  templateUrl: './beats.component.html',
  styleUrl: './beats.component.css',
  changeDetection: ChangeDetectionStrategy.Default
})
export class BeatsComponent implements OnInit{
  isAdmin = this._authService.isAdmin();
  isLoggedIn = this._authService.isLoggedIn();
  productions: Production[] = [];
  allProductions: Production[] = [];
  genres: string[] = Object.values(Genre);
  currentSongIndex: number = 1;
  isPlaying: boolean = false;
  currentSong: Production | null = null;
  progressValue: number = 0;
  selectedGenre: string = 'all';
  searchText: string = '';
  currentProductionIdToDelete: number | null = null;
  showLicenseModal = false;
  currentProductionId: number | null = null;
  licenseTypes = ['BASIC', 'PREMIUM', 'TRACKOUT', 'UNLIMITED', 'EXCLUSIVE'];
  searchTerms = new Subject<any>();




  constructor(private _authService: AuthService,
              private _productionService: ProductionService,
              private _shoppingCartService: ShoppingCartService,
              private _wishlistService: WishlistService) {
  }

  @ViewChild('audioPlayer') audioPlayer!: ElementRef<HTMLAudioElement>;

  ngOnInit() {
    this.genres = Object.values(Genre);
    this.fetchProductions();

    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged((prev, curr) => JSON.stringify(prev) === JSON.stringify(curr))
    ).subscribe(criteria => {
      this.searchText = criteria.searchText;
      this.selectedGenre = criteria.selectedGenre;
      this.filterProductions();
    });
  }


  addToCart(itemId: number, licenseType: LicenseType): void {
    const username = this._authService.getUsername();
    if (username) {
      this._shoppingCartService.addItemToCart(username, itemId, true, licenseType).subscribe({
        next: () => console.log('Item added to cart successfully'),
        error: (error) => console.error('Failed to add item to cart', error)
      });
    } else {
      console.error('User is not logged in');
    }
  }

  addToWishlist(productionId: number): void {
    const username = this._authService.getUsername();
    if (username) {
      this._wishlistService.addItemToWishlist(username, productionId, undefined).subscribe({
        next: () => console.log(`Production ${productionId} added to wishlist successfully`),
        error: (error) => console.error(`Failed to add production to wishlist`, error)
      });
    } else {
      console.error('User is not logged in');
    }
  }


  fetchProductions() {
    this._productionService.getAllProductions().subscribe({
      next: (productions) => {
        this.allProductions = productions;
        this.productions = productions;
      }, error: (error) =>
        console.error('Failed to fetch productions', error),
    });
  }

  filterProductions(){
    this.productions = this.allProductions.filter(production => {
      const matchesGenre = this.selectedGenre === 'all' || production.genre === this.selectedGenre;
      const matchesTitle = production.title.toLowerCase().includes(this.searchText.toLowerCase());
      return matchesGenre && matchesTitle;
    });

  }

  playSong(production: Production): void {
    const player: HTMLAudioElement = this.audioPlayer.nativeElement;
    this.currentSong = production;
    this.currentSongIndex = this.productions.findIndex(p => p.id === production.id);
    player.src = production.audioMp3;

    const updateProgress = () => {
      this.progressValue = (player.currentTime / player.duration) * 100;
      if (this.progressValue < 100) {
        requestAnimationFrame(updateProgress);
      }
    };

    player.play().then(() => {
      this.isPlaying = true;
      requestAnimationFrame(updateProgress);
    })
      .catch(error => console.error('Playback failed', error));
  }

  playPrevious(): void {
    if (this.currentSongIndex <= 0) {
      this.currentSongIndex = this.productions.length - 1;
    } else {
      this.currentSongIndex--;
    }
    this.playSong(this.productions[this.currentSongIndex]);
  }

  playNext(): void {
    if (this.currentSongIndex >= this.productions.length - 1) {
      this.currentSongIndex = 0;
    } else {
      this.currentSongIndex++;
    }
    this.playSong(this.productions[this.currentSongIndex]);
  }


  togglePlayPause(): void {
    const player: HTMLAudioElement = this.audioPlayer.nativeElement;
    if (this.isPlaying) {
      player.pause();
    } else {
      if (player.src) {
        player.play().catch(error => console.error('Playback failed', error));
      } else if (this.productions.length > 0) {
        this.playSong(this.productions[0]);
      }
    }
    this.isPlaying = !this.isPlaying;
  }

  seekTo(event: any): void {
    const player: HTMLAudioElement = this.audioPlayer.nativeElement;
    const seekTime = (event.target.value / 100) * player.duration;
    player.currentTime = seekTime;
  }

  changeVolume(event: any): void {
    this.audioPlayer.nativeElement.volume = event.target.value;
  }

  formatDuration(seconds: number): string {
    const minutes: number = Math.floor(seconds / 60);
    const remainingSeconds: number = seconds % 60;
    const formattedMinutes: string = minutes.toString().padStart(2, '0');
    const formattedSeconds: string = remainingSeconds.toString().padStart(2, '0');
    return `${formattedMinutes}:${formattedSeconds}`;
  }

  openDeleteModal(productionId: number): void {
    if (typeof productionId === 'undefined' || productionId === null) {
      console.error('Production ID is undefined or null');
      return;
    }
    this.currentProductionIdToDelete = productionId;
    document.getElementById('deleteModal')!.style.display = 'block';
  }

  closeModal(): void {
    document.getElementById('deleteModal')!.style.display = 'none';
  }

  deleteProduction(): void {
    if (this.currentProductionIdToDelete !== null) {
      this._productionService.deleteProductions(this.currentProductionIdToDelete).subscribe({
        next: () => {
          this.fetchProductions();
          this.closeModal();
        },
        error: (error) => console.error('Error deleting production', error)
      });
    }
  }

  openLicenseModal(productionId: number): void {
    this.currentProductionId = productionId;
    if (this.currentProductionId === null) {
      console.error('No production selected');
      return;
    }
    this.showLicenseModal = true;
  }

  getLicensePrice(licenseType: string): number {
    switch (licenseType) {
      case 'BASIC':
        return 24.95;
      case 'PREMIUM':
        return 49.95;
      case 'TRACKOUT':
        return 99.95;
      case 'UNLIMITED':
        return 199.95;
      case 'EXCLUSIVE':
        return 1000.00;
      default:
        return 0;
    }
  }


  getLicenseType(license: string): LicenseType {
    switch (license.toUpperCase()) {
      case 'BASIC':
        return LicenseType.BASIC;
      case 'PREMIUM':
        return LicenseType.PREMIUM;
      case 'TRACKOUT':
        return LicenseType.TRACKOUT;
      case 'UNLIMITED':
        return LicenseType.UNLIMITED;
      case 'EXCLUSIVE':
        return LicenseType.EXCLUSIVE;
      default:
        throw new Error('Invalid license type');
    }
  }

  protected readonly LicenseType = LicenseType;
}
